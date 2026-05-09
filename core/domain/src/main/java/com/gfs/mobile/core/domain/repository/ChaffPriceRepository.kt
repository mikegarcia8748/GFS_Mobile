package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.param.AddChaffPriceParams
import com.gfs.mobile.core.domain.model.price.ChaffPriceModel
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface ChaffPriceRepository {
    fun getChaffPrice(): Flow<NetworkResource<BaseResponse<List<ChaffPriceModel>>>>
    fun addChaffPrice(params: AddChaffPriceParams): Flow<NetworkResource<BaseResponse<ChaffPriceModel>>>
    fun getChaffPriceFromCache(): Flow<ChaffPriceModel?>
    suspend fun saveChaffPriceToCache(value: ChaffPriceModel)
}
