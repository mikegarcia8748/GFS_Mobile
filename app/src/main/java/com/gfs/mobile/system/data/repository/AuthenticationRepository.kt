package com.gfs.mobile.system.data.repository

import com.gfs.mobile.system.data.remote.APIService
import com.gfs.mobile.system.data.remote.networkBoundResource
import com.gfs.mobile.system.data.remote.param.AuthenticateMPINParams
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val apiService: APIService
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
}