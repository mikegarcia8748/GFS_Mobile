package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.domain.model.customer.CustomerModel
import com.gfs.mobile.core.domain.model.param.AddCustomerParams
import com.gfs.mobile.core.domain.repository.CustomerRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val apiService: APIService,
) : CustomerRepository {

    override fun getCustomers(
        index: Int
    ): Flow<NetworkResource<BaseResponse<List<CustomerModel>>>> = networkBoundResource(
        fetch = {
            apiService.getCustomers(index)
        }
    )

    override fun searchCustomer(
        name: String
    ): Flow<NetworkResource<BaseResponse<List<CustomerModel>>>> = networkBoundResource(
        fetch = {
            apiService.searchCustomer(name = name)
        }
    )

    override fun addCustomer(
        params: AddCustomerParams
    ): Flow<NetworkResource<BaseResponse<CustomerModel>>> = networkBoundResource(
        fetch = {
            apiService.addCustomer(params)
        }
    )
}
