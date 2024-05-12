package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.local.preferences.pricing.chaff.ChaffPriceCache
import com.gfs.mobile.system.data.local.sharedPreferenceResource
import com.gfs.mobile.system.data.model.price.ChaffPriceModel
import com.gfs.mobile.system.data.param.AddChaffPriceParams
import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import javax.inject.Inject

class ChaffPriceRepository @Inject constructor(
    private val apiService: APIService,
    private val chaffPriceCache: ChaffPriceCache
) {

    fun addChaffPrice(
        params: AddChaffPriceParams
    ) = networkBoundResource (
        fetch = {
            apiService.addChaffPrice(params)
        }
    )

    fun getChaffPrice() = networkBoundResource(
        fetch = {
            apiService.getChaffPrice()
        }
    )

    fun getChaffPriceFromCache() = sharedPreferenceResource(
        fetch = {
            chaffPriceCache.getChaffPrice()
        }
    )

    fun saveChaffPriceToCache(
        value: ChaffPriceModel
    ) = sharedPreferenceResource(
        parameter = value,
        save = { chaffPriceCache.saveChaffPrice(it) }
    )
}