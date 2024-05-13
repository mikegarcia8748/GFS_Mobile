package com.gfs.mobile.system.data.local.preferences.pricing.mill

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gfs.mobile.system.data.model.price.MillPriceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class MillPriceCacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : MillPriceCache {

    private object PreferencesKey {
        val millPrice = stringPreferencesKey(name = "mill_price_cache")
    }

    override suspend fun saveMillPrice(value: MillPriceModel) {
        dataStore.edit { preference ->
            preference[PreferencesKey.millPrice] = json.encodeToString(value)
        }
    }

    override fun getMillPrice(): Flow<MillPriceModel?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) { emit(emptyPreferences()) }
                else { throw exception }
            }
            .map { preference ->
                val cache = preference[PreferencesKey.millPrice] ?: ""
                if (cache.isNotEmpty()) json.decodeFromString(cache) else null
            }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.millPrice)
        }
    }
}