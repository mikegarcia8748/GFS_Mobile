package com.gfs.mobile.system.data.local.preferences.user

import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import kotlinx.coroutines.flow.Flow

interface AuthenticationCache {

    suspend fun saveAuthentication(value: AuthenticationMPINModel)

    fun getAuthenticationCache(): Flow<AuthenticationMPINModel?>

    suspend fun clear()
}