package com.gfs.mobile.system.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class DefaultHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept", "Application/json")
        return chain.proceed(builder.build())
    }
}