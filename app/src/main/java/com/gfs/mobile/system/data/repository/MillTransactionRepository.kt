package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.local.preferences.millbilling.MillBillingCache
import com.gfs.mobile.system.data.param.MillTransactionParams
import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import javax.inject.Inject

class MillTransactionRepository @Inject constructor(
    private val apiService: APIService,
    private val millBillingCache: MillBillingCache
) {

    fun saveMillTransaction(
        params: MillTransactionParams
    ) = networkBoundResource(
        fetch = {
            apiService.saveMillTransaction(params)
        }
    )

    fun getMillBillingCache() = millBillingCache.getMillBilling()

    suspend fun saveMillBillingToCache(
        value: MillTransactionParams
    ) = millBillingCache.saveMillBilling(value)

    suspend fun clearMillBillingCache() = millBillingCache.clear()
}