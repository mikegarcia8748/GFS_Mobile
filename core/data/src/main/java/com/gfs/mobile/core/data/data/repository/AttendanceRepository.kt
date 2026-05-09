package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.domain.model.AttendanceModel
import com.gfs.mobile.core.domain.model.AttendanceTodayModel
import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.repository.AttendanceRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : AttendanceRepository {

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
}
