package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.data.data.local.preferences.millbilling.MillBillingCache
import com.gfs.mobile.core.domain.model.param.MillTransactionParams
import com.gfs.mobile.core.domain.repository.MillTransactionRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MillTransactionRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val millBillingCache: MillBillingCache
) : MillTransactionRepository {

    override fun saveMillTransaction(
        params: MillTransactionParams
    ): Flow<NetworkResource<BaseResponse<Unit>>> = networkBoundResource(
        fetch = {
            apiService.saveMillTransaction(params)
        }
    )

    override fun getMillBillingCache(): Flow<MillTransactionParams?> = millBillingCache.getMillBilling()

    override suspend fun saveMillBillingToCache(
        value: MillTransactionParams
    ) = millBillingCache.saveMillBilling(value)

    override suspend fun clearMillBillingCache() = millBillingCache.clear()
}
