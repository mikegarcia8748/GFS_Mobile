package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.param.MillTransactionParams
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface MillTransactionRepository {
    fun saveMillTransaction(params: MillTransactionParams): Flow<NetworkResource<BaseResponse<Unit>>>
    fun getMillBillingCache(): Flow<MillTransactionParams?>
    suspend fun saveMillBillingToCache(value: MillTransactionParams)
    suspend fun clearMillBillingCache()
}
