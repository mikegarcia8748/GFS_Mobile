package com.gfs.mobile.core.data.data.remote

import com.gfs.mobile.core.domain.util.NetworkResource
import kotlinx.coroutines.flow.*
import retrofit2.Response
import timber.log.Timber

inline fun<T> networkBoundResource(
    crossinline fetch: suspend () -> Response<T>
) = flow<NetworkResource<T>> {

    emit(NetworkResource.Loading(null))

    try{
        val data = fetch()

        if (data.isSuccessful) {
            val body = data.body()
            if (body != null) {
                emit(NetworkResource.Success(body))
            } else {
                // For Unit or empty body success
                @Suppress("UNCHECKED_CAST")
                emit(NetworkResource.Success(Unit as T))
            }
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
