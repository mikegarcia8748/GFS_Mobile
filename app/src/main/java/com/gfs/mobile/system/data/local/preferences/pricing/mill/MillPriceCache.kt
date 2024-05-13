package com.gfs.mobile.system.data.local.preferences.pricing.mill

import com.gfs.mobile.system.data.model.price.MillPriceModel
import kotlinx.coroutines.flow.Flow

interface MillPriceCache {

    suspend fun saveMillPrice(value: MillPriceModel)

    fun getMillPrice(): Flow<MillPriceModel?>

    suspend fun clear()
}