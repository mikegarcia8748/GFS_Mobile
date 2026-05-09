package com.gfs.mobile.core.data.data.local.preferences.pricing.chaff

import com.gfs.mobile.core.domain.model.price.ChaffPriceModel
import kotlinx.coroutines.flow.Flow

interface ChaffPriceCache {

    suspend fun saveChaffPrice(value: ChaffPriceModel)

    fun getChaffPrice(): Flow<ChaffPriceModel?>

    suspend fun clear()
}
