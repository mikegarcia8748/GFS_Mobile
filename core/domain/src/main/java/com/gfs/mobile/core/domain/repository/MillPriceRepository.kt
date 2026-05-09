package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.param.AddMillPriceParams
import com.gfs.mobile.core.domain.model.price.MillPriceModel
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface MillPriceRepository {
    fun getMillPrice(): Flow<NetworkResource<BaseResponse<List<MillPriceModel>>>>
    fun addMillPrice(params: AddMillPriceParams): Flow<NetworkResource<BaseResponse<MillPriceModel>>>
    suspend fun saveMillPriceCache(value: MillPriceModel)
    fun getMillPriceFromCache(): Flow<MillPriceModel?>
}
