package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import javax.inject.Inject

class WorkerRepository @Inject constructor(
    private val apiService: APIService
) {

    fun getEmployee() = networkBoundResource(
        fetch = {
            apiService.getEmployees()
        }
    )
}