package com.gfs.mobile.system.data.remote

import kotlinx.coroutines.flow.*
import retrofit2.Response
import timber.log.Timber

inline fun<T> networkBoundResource(
    crossinline fetch: suspend () -> Response<T>
) = flow {

    emit(NetworkResource.Loading(null))

    try{
        val data = fetch()
        val body = data.body()

        if (data.isSuccessful) {
            emit(NetworkResource.Success(body))
        } else {
            val throwable = Throwable(message = "An error has occurred!, Please try again later.")
            Timber.e(throwable)
            emit(NetworkResource.Error(throwable, null))
        }
    } catch (throwable: Throwable) {
        Timber.e(throwable)
        emit(NetworkResource.Error(throwable, null))
    }
}