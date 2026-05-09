package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.AttendanceModel
import com.gfs.mobile.core.domain.model.AttendanceTodayModel
import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun getAttendanceToday(): Flow<NetworkResource<BaseResponse<List<AttendanceTodayModel>>>>
    fun getEmployeeAttendance(id: String): Flow<NetworkResource<BaseResponse<List<AttendanceModel>>>>
    fun createAttendance(params: CreateAttendanceParams): Flow<NetworkResource<BaseResponse<Unit>>>
}
