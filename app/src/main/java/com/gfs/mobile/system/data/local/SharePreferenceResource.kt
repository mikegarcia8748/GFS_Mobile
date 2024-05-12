package com.gfs.mobile.system.data.local

import com.gfs.mobile.system.data.local.room.PreferenceResource
import kotlinx.coroutines.flow.*
import timber.log.Timber

inline fun<ResultType> sharedPreferenceResource(
  crossinline fetch: suspend () -> Flow<ResultType>,
) = flow {

    val data = fetch().first()

    emit(PreferenceResource.Result(data))
}


inline fun<Parameter> sharedPreferenceResource(
    parameter: Parameter,
    crossinline save: suspend (Parameter) -> Unit,
) = flow {

    val data = save(parameter)
    emit(PreferenceResource.Result(data))
}