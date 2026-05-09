package com.gfs.mobile.core.data.data.local.preferences.user.previoususer

import com.gfs.mobile.core.domain.model.authentication.AuthenticationMPINModel
import kotlinx.coroutines.flow.Flow

interface PreviousUserCache {

    suspend fun savePreviousUser(value: String)

    fun getPreviousUser(): Flow<String?>

    suspend fun clear()
}
