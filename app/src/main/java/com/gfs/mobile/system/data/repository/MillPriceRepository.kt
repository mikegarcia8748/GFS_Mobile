package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.local.preferences.pricing.mill.MillPriceCache
import com.gfs.mobile.system.data.model.price.MillPriceModel
import com.gfs.mobile.system.data.param.AddMillPriceParams
import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import javax.inject.Inject

class MillPriceRepository @Inject constructor(
    private val apiService: APIService,
    private val millPriceCache: MillPriceCache
){

    fun addMillPrice(
        params: AddMillPriceParams
    ) = networkBoundResource(
        fetch = {
            apiService.addMillPrice(params)
        }
    )

    fun getMillPrice() = networkBoundResource(
        fetch = {
            apiService.getMillPrice()
        }
    )

    suspend fun saveMillPriceCache(
        value: MillPriceModel
    ) = millPriceCache.saveMillPrice(value)

    fun getMillPriceFromCache() = millPriceCache.getMillPrice()
}