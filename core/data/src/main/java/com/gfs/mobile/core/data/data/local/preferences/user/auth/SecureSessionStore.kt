package com.gfs.mobile.core.data.data.local.preferences.user.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SecureSessionStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val pinnedAdminId = stringPreferencesKey("pinned_admin_id")
    }

    suspend fun savePinnedAdminId(adminId: String) {
        dataStore.edit { preferences ->
            preferences[Keys.pinnedAdminId] = adminId
        }
    }

    fun getPinnedAdminId(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[Keys.pinnedAdminId]
        }
    }
}
