package com.gfs.mobile.system.data.local

import com.gfs.mobile.system.data.local.room.PreferenceResource
import kotlinx.coroutines.flow.*
import timber.log.Timber

inline fun<ResultType> sharedPreferenceResource(
  crossinline fetch: suspend () -> Flow<ResultType>,
) = flow {

    try {
        val data = fetch().first()

        emit(PreferenceResource.Success(data))
    } catch (throwable: Throwable) {
        Timber.e(throwable)
        emit(PreferenceResource.Error(throwable, null))
    }

//    val flow = if (shouldFetch()) {
//
//        try {
//          emit(PreferenceResource.Success(data))
//        } catch (throwable: Throwable) {
//          Timber.e(throwable)
//          emit(PreferenceResource.Error(throwable, null))
//        }
//    } else {
//
//        try {
//          save(parameter)
//          emit(PreferenceResource.Success(null))
//        } catch (throwable: Throwable) {
//          Timber.e(throwable)
//          emit(PreferenceResource.Error(throwable, null))
//        }
//    }
  
//  emitAll(flow)
}


inline fun<Parameter> sharedPreferenceResource(
    parameter: Parameter,
    crossinline save: suspend (Parameter) -> Unit,
) = flow {

    try {
        val data = save(parameter)
        emit(PreferenceResource.Success(data))
    } catch (throwable: Throwable) {
        Timber.e(throwable)
        emit(PreferenceResource.Error(throwable, null))
    }

//    val flow = if (shouldFetch()) {
//
//        try {
//          emit(PreferenceResource.Success(data))
//        } catch (throwable: Throwable) {
//          Timber.e(throwable)
//          emit(PreferenceResource.Error(throwable, null))
//        }
//    } else {
//
//        try {
//          save(parameter)
//          emit(PreferenceResource.Success(null))
//        } catch (throwable: Throwable) {
//          Timber.e(throwable)
//          emit(PreferenceResource.Error(throwable, null))
//        }
//    }

//  emitAll(flow)
}