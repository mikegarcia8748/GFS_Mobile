package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.data.data.local.preferences.pricing.chaff.ChaffPriceCache
import com.gfs.mobile.core.domain.model.price.ChaffPriceModel
import com.gfs.mobile.core.domain.model.param.AddChaffPriceParams
import com.gfs.mobile.core.domain.repository.ChaffPriceRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChaffPriceRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val chaffPriceCache: ChaffPriceCache
) : ChaffPriceRepository {

    override fun addChaffPrice(
        params: AddChaffPriceParams
    ): Flow<NetworkResource<BaseResponse<ChaffPriceModel>>> = networkBoundResource (
        fetch = {
            apiService.addChaffPrice(params)
        }
    )

    override fun getChaffPrice(): Flow<NetworkResource<BaseResponse<List<ChaffPriceModel>>>> = networkBoundResource(
        fetch = {
            apiService.getChaffPrice()
        }
    )

    override fun getChaffPriceFromCache(): Flow<ChaffPriceModel?> = chaffPriceCache.getChaffPrice()

    override suspend fun saveChaffPriceToCache(
        value: ChaffPriceModel
    ) = chaffPriceCache.saveChaffPrice(value)
}
