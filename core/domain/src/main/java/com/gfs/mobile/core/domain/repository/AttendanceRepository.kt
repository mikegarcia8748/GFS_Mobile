package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.AttendanceModel
import com.gfs.mobile.core.domain.model.AttendanceTodayModel
import com.gfs.mobile.core.domain.model.attendance.AttendanceRecord
import com.gfs.mobile.core.domain.model.attendance.AttendanceSummary
import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    // Legacy methods
    fun getAttendanceToday(): Flow<NetworkResource<BaseResponse<List<AttendanceTodayModel>>>>
    fun getEmployeeAttendance(id: String): Flow<NetworkResource<BaseResponse<List<AttendanceModel>>>>
    fun createAttendance(params: CreateAttendanceParams): Flow<NetworkResource<BaseResponse<Unit>>>

    // Unified / Local-First methods
    fun getConsolidatedAttendanceToday(): Flow<NetworkResource<List<AttendanceSummary>>>
    fun recordAttendance(params: CreateAttendanceParams): Flow<NetworkResource<Unit>>
    fun getAttendanceHistory(workerId: String): Flow<List<AttendanceRecord>>
    fun syncUnsyncedRecords(): Flow<NetworkResource<Unit>>
}
