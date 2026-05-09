package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.GetEmployeesModel
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface WorkerRepository {
    fun getEmployees(): Flow<NetworkResource<BaseResponse<List<GetEmployeesModel>>>>
}
