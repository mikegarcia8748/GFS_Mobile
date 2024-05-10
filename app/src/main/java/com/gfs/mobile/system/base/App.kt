package com.gfs.mobile.system.base

import android.app.Application
import com.gfs.mobile.system.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()


        // Enable logging on debug version.
        // Added logging on dev release for QA purposes.
        if (BuildConfig.DEBUG ||
            (BuildConfig.FLAVOR == "dev")
        ) {
            // DebugTree has all usual logging functionality
            Timber.plant(Timber.DebugTree())
        }
    }
}