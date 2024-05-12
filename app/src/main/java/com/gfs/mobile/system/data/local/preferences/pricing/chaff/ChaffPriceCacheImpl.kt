package com.gfs.mobile.system.data.local.preferences.pricing.chaff

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gfs.mobile.system.data.model.price.ChaffPriceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class ChaffPriceCacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : ChaffPriceCache {


    private object PreferencesKey {
        val chaffPrice = stringPreferencesKey(name = "chaff_price_cache")
    }

    override suspend fun saveChaffPrice(value: ChaffPriceModel) {
        dataStore.edit { preference ->
            preference[PreferencesKey.chaffPrice] = json.encodeToString(value)
        }
    }

    override fun getChaffPrice(): Flow<ChaffPriceModel?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) { emit(emptyPreferences()) }
                else { throw exception }
            }
            .map { preference ->
                val cache = preference[PreferencesKey.chaffPrice] ?: ""
                if (cache.isNotEmpty()) json.decodeFromString(cache) else null
            }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.chaffPrice)
        }
    }
}