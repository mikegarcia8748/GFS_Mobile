package com.gfs.mobile.core.data.data.local.preferences.user.auth

import com.gfs.mobile.core.domain.model.authentication.AuthenticationMPINModel
import kotlinx.coroutines.flow.Flow

interface AuthenticationCache {

    suspend fun saveAuthentication(value: AuthenticationMPINModel)

    fun getAuthenticationCache(): Flow<AuthenticationMPINModel?>

    suspend fun clear()
}
