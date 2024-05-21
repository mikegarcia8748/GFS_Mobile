package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.param.CreateAttendanceParams
import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import javax.inject.Inject

class AttendanceRepository @Inject constructor(
    private val apiService: APIService
) {

    fun getAttendanceToday() = networkBoundResource(
        fetch = {
            apiService.getAttendanceToday()
        }
    )

    fun createAttendance(
        params: CreateAttendanceParams
    ) = networkBoundResource(
        fetch = {
            apiService.createAttendance(params)
        }
    )
}