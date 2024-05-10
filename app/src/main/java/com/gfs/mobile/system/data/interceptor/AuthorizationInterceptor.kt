package com.gfs.mobile.system.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor : Interceptor {

    private var accessToken = ""

    fun setAccessToken(value: String) {
        accessToken = value
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        Timber.d("Authorization Interceptor Token: $accessToken")
        if (request.header("No-Authentication") == null) {
            if (accessToken.isNotEmpty()) {
                request = request
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken").build()
            }
        }
        return chain.proceed(request)
    }
}