package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val apiService: APIService,
) {

    fun searchCustomer(
        name: String
    ) = networkBoundResource(
        fetch = {
            apiService.searchCustomer(name = name)
        }
    )
}