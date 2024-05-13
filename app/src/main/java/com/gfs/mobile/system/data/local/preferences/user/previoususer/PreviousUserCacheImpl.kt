package com.gfs.mobile.system.data.local.preferences.user.previoususer

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class PreviousUserCacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : PreviousUserCache {

    private object PreferencesKey {
        val previousUser = stringPreferencesKey(name = "previous_user_cache")
    }

    override suspend fun savePreviousUser(value: String) {
        dataStore.edit { preference ->
            preference[PreferencesKey.previousUser] = json.encodeToString(value)
        }
    }

    override fun getPreviousUser(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) { emit(emptyPreferences()) }
                else { throw exception }
            }
            .map { preference ->
                val cache = preference[PreferencesKey.previousUser] ?: ""
                if (cache.isNotEmpty()) json.decodeFromString(cache) else null
            }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.previousUser)
        }
    }
}