package com.gfs.mobile.system.data.local.room

sealed class PreferenceResource<out T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : PreferenceResource<T>(data)

    class Error<T>(throwable: Throwable, data: T? = null) : PreferenceResource<T>(data, throwable)
}