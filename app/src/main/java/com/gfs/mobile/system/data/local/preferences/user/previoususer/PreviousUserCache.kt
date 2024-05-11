package com.gfs.mobile.system.data.local.preferences.user.previoususer

import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import kotlinx.coroutines.flow.Flow

interface PreviousUserCache {

    suspend fun savePreviousUser(value: String)

    fun getPreviousUser(): Flow<String?>

    suspend fun clear()
}