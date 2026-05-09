package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.data.data.local.room.attendance.AttendanceDao
import com.gfs.mobile.core.data.data.mapper.toDomain
import com.gfs.mobile.core.data.data.mapper.toEntity
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import com.gfs.mobile.core.domain.model.AttendanceModel
import com.gfs.mobile.core.domain.model.AttendanceTodayModel
import com.gfs.mobile.core.domain.model.attendance.AttendanceRecord
import com.gfs.mobile.core.domain.model.attendance.AttendanceStatus
import com.gfs.mobile.core.domain.model.attendance.AttendanceSummary
import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.repository.AttendanceRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val attendanceDao: AttendanceDao
) : AttendanceRepository {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Legacy methods
    override fun getAttendanceToday(): Flow<NetworkResource<BaseResponse<List<AttendanceTodayModel>>>> = networkBoundResource(
        fetch = {
            apiService.getAttendanceToday()
        }
    )

    override fun getEmployeeAttendance(id: String): Flow<NetworkResource<BaseResponse<List<AttendanceModel>>>> = networkBoundResource(
        fetch = {
            apiService.getEmployeeAttendance(id)
        }
    )

    override fun createAttendance(
        params: CreateAttendanceParams
    ): Flow<NetworkResource<BaseResponse<Unit>>> = networkBoundResource(
        fetch = {
            apiService.createAttendance(params)
        }
    )

    // Unified / Local-First methods
    override fun getConsolidatedAttendanceToday(): Flow<NetworkResource<List<AttendanceSummary>>> = flow {
        emit(NetworkResource.Loading())
        
        // 1. Get the baseline worker list from legacy API (temporary until unified WorkerRepository)
        val workersResponse = apiService.getAttendanceToday()
        if (workersResponse.isSuccessful) {
            val workerBaselines = workersResponse.body()?.data ?: emptyList()
            val todayDate = dateFormat.format(Date())

            // 2. Combine with local records
            attendanceDao.getAttendanceByDate(todayDate).collect { localEntries ->
                val summaries = workerBaselines.map { worker ->
                    val entries = localEntries
                        .filter { it.workerID == worker.workerID }
                        .map { it.toDomain() }
                    
                    AttendanceSummary(
                        workerID = worker.workerID.orEmpty(),
                        fullName = worker.fullName.orEmpty(),
                        userName = worker.userName.orEmpty(),
                        entries = entries,
                        overallStatus = entries.lastOrNull()?.status
                    )
                }
                emit(NetworkResource.Success(summaries))
            }
        } else {
            emit(NetworkResource.Error(Exception("Failed to fetch worker list")))
        }
    }

    override fun recordAttendance(params: CreateAttendanceParams): Flow<NetworkResource<Unit>> = flow {
        emit(NetworkResource.Loading())
        try {
            val record = params.toRecord()
            attendanceDao.upsertAttendance(record.toEntity(isSynced = false))
            emit(NetworkResource.Success(Unit))
            
            // Trigger sync (Step 12 will handle background sync, but repo can initiate)
        } catch (e: Exception) {
            emit(NetworkResource.Error(e))
        }
    }

    override fun getAttendanceHistory(workerId: String): Flow<List<AttendanceRecord>> {
        return attendanceDao.getAttendanceByWorker(workerId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun syncUnsyncedRecords(): Flow<NetworkResource<Unit>> = flow {
        emit(NetworkResource.Loading())
        val unsynced = attendanceDao.getUnsyncedAttendance()
        if (unsynced.isEmpty()) {
            emit(NetworkResource.Success(Unit))
            return@flow
        }

        var hasError = false
        unsynced.forEach { entity ->
            val params = CreateAttendanceParams(
                workerID = entity.workerID,
                entryBy = entity.entryBy,
                businessLineID = entity.businessLineID,
                status = entity.status
            )
            val response = apiService.createAttendance(params)
            if (response.isSuccessful) {
                attendanceDao.markAsSynced(listOf(entity.id))
            } else {
                hasError = true
            }
        }

        if (hasError) {
            emit(NetworkResource.Error(Exception("Some records failed to sync")))
        } else {
            emit(NetworkResource.Success(Unit))
        }
    }

    private fun CreateAttendanceParams.toRecord(): AttendanceRecord {
        return AttendanceRecord(
            workerID = workerID.orEmpty(),
            businessLineID = businessLineID ?: "MILL",
            date = dateFormat.format(Date()),
            status = AttendanceStatus.valueOf(status ?: "PRESENT"),
            entryBy = entryBy.orEmpty(),
            timestamp = System.currentTimeMillis()
        )
    }
}
