package com.gfs.mobile.system.data.remote

sealed class NetworkResource<out T> (
    val data: T? = null,
    val error: Throwable? = null
) {

    class Success<T>(data: T) : NetworkResource<T>(data)

    class Loading<T>(data: T? = null) : NetworkResource<T>(data)

    class Error<T>(throwable: Throwable, data: T?= null) : NetworkResource<T>(data, throwable)
}