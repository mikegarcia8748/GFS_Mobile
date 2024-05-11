package com.gfs.mobile.system.data.local.preferences.user.auth

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

class AuthenticationCacheImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
): AuthenticationCache {

    private object PreferencesKey {
        val authentication = stringPreferencesKey(name = "authentication_cache")
    }

    override suspend fun saveAuthentication(value: AuthenticationMPINModel) {
        dataStore.edit { preference ->
            preference[PreferencesKey.authentication] = json.encodeToString(value)
        }
    }

    override fun getAuthenticationCache(): Flow<AuthenticationMPINModel?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) { emit(emptyPreferences()) }
                else { throw exception }
            }
            .map { preference ->
                val cache = preference[PreferencesKey.authentication] ?: ""
                if (cache.isNotEmpty()) json.decodeFromString(cache) else null
            }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.authentication)
        }
    }
}