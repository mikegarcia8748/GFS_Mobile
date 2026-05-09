package com.gfs.mobile.core.domain.repository

import com.gfs.mobile.core.domain.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.core.domain.model.authorizeusers.AuthorizeUsers
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    // Legacy/Local MPIN logic
    fun getAuthorizeUsers(): Flow<NetworkResource<BaseResponse<List<AuthorizeUsers>>>>
    fun authenticateMPIN(userName: String, mpin: String): Flow<NetworkResource<BaseResponse<AuthenticationMPINModel>>>
    
    // Firebase Logic
    fun signInWithCustomToken(token: String): Flow<NetworkResource<Unit>>
    fun signInWithEmail(email: String, pass: String): Flow<NetworkResource<Unit>>
    fun signOut(): Flow<NetworkResource<Unit>>
    fun isUserAuthenticated(): Flow<Boolean>

    // Shared session management
    fun getAuthenticationToken(): Flow<AuthenticationMPINModel?>
    suspend fun saveAuthenticationToken(value: AuthenticationMPINModel)
    suspend fun savePreviousUsername(value: String)
    fun getPreviousUser(): Flow<String?>
}
