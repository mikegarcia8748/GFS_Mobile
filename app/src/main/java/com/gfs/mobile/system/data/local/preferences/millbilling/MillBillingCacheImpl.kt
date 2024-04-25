package com.gfs.mobile.system.data.local.preferences.millbilling

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gfs.mobile.system.data.model.MillTransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class MillBillingCacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : MillBillingCache {

    private object PreferencesKey {
        val millBilling = stringPreferencesKey(name = "mill_billing")
    }

    override suspend fun saveMillBilling(value: MillTransactionModel) {
        dataStore.edit { preference ->
            preference[PreferencesKey.millBilling] = json.encodeToString(value)
        }
    }

    override fun getMillBilling(): Flow<MillTransactionModel?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) { emit(emptyPreferences()) }
                else { throw exception }
            }
            .map { preference ->
                val cache = preference[PreferencesKey.millBilling] ?: ""
                if (cache.isNotEmpty()) json.decodeFromString(cache) else null
            }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.millBilling)
        }
    }

}