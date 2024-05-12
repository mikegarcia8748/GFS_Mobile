package com.gfs.mobile.system.data.local.preferences.pricing.chaff

import com.gfs.mobile.system.data.model.price.ChaffPriceModel
import kotlinx.coroutines.flow.Flow

interface ChaffPriceCache {

    suspend fun saveChaffPrice(value: ChaffPriceModel)

    fun getChaffPrice(): Flow<ChaffPriceModel?>

    suspend fun clear()
}