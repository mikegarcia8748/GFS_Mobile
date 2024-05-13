package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.local.preferences.user.auth.AuthenticationCache
import com.gfs.mobile.system.data.local.preferences.user.previoususer.PreviousUserCache
import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import com.gfs.mobile.system.data.remote.param.AuthenticateMPINParams
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val apiService: APIService,
    private val authenticationCache: AuthenticationCache,
    private val previousUserCache: PreviousUserCache
) {

    fun getAuthorizeUsers() = networkBoundResource(
        fetch = {
            apiService.getAuthorizeUsers()
        }
    )

    fun authenticateMPIN(
        userName: String,
        mpin: String
    ) = networkBoundResource(
        fetch = {

            val authenticateMPINParams = AuthenticateMPINParams(
                userName = userName,
                mpin = mpin
            )
            apiService.authenticateMPIN(authenticateMPINParams)
        }
    )

    fun getAuthenticationToken() = authenticationCache.getAuthenticationCache()

    suspend fun saveAuthenticationToken(
        value: AuthenticationMPINModel
    ) = authenticationCache.saveAuthentication(value)

    suspend fun savePreviousUsername(
        value: String
    ) = previousUserCache.savePreviousUser(value)

    fun getPreviousUser() = previousUserCache.getPreviousUser()
}