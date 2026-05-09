package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.customer.CustomerModel
import com.gfs.mobile.core.domain.model.param.AddCustomerParams
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getCustomers(index: Int): Flow<NetworkResource<BaseResponse<List<CustomerModel>>>>
    fun searchCustomer(name: String): Flow<NetworkResource<BaseResponse<List<CustomerModel>>>>
    fun addCustomer(params: AddCustomerParams): Flow<NetworkResource<BaseResponse<CustomerModel>>>
}
