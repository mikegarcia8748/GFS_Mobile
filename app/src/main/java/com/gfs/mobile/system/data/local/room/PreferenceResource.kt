package com.gfs.mobile.system.data.local.room

sealed class PreferenceResource<out T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Result<T>(data: T) : PreferenceResource<T>(data)
}