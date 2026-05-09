package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.data.data.local.preferences.pricing.mill.MillPriceCache
import com.gfs.mobile.core.domain.model.price.MillPriceModel
import com.gfs.mobile.core.domain.model.param.AddMillPriceParams
import com.gfs.mobile.core.domain.repository.MillPriceRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MillPriceRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val millPriceCache: MillPriceCache
) : MillPriceRepository {

    override fun addMillPrice(
        params: AddMillPriceParams
    ): Flow<NetworkResource<BaseResponse<MillPriceModel>>> = networkBoundResource(
        fetch = {
            apiService.addMillPrice(params)
        }
    )

    override fun getMillPrice(): Flow<NetworkResource<BaseResponse<List<MillPriceModel>>>> = networkBoundResource(
        fetch = {
            apiService.getMillPrice()
        }
    )

    override suspend fun saveMillPriceCache(
        value: MillPriceModel
    ) = millPriceCache.saveMillPrice(value)

    override fun getMillPriceFromCache(): Flow<MillPriceModel?> = millPriceCache.getMillPrice()
}
