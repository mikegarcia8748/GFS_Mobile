package com.gfs.mobile.core.domain.util

sealed class NetworkResource<out T> (
    open val data: T? = null,
    open val error: Throwable? = null
) {

    class Success<T>(override val data: T) : NetworkResource<T>(data)

    class Loading<T>(override val data: T? = null) : NetworkResource<T>(data)

    class Error<T>(throwable: Throwable, override val data: T?= null) : NetworkResource<T>(data, throwable)
}
