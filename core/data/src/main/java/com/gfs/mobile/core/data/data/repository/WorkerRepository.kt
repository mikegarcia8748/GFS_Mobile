package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.domain.model.GetEmployeesModel
import com.gfs.mobile.core.domain.repository.WorkerRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkerRepositoryImpl @Inject constructor(
    private val apiService: APIService,
) : WorkerRepository {

    override fun getEmployees(): Flow<NetworkResource<BaseResponse<List<GetEmployeesModel>>>> = networkBoundResource(
        fetch = {
            apiService.getEmployees()
        }
    )
}
