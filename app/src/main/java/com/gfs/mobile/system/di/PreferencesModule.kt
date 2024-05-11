package com.gfs.mobile.system.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.gfs.mobile.system.data.local.preferences.millbilling.MillBillingCache
import com.gfs.mobile.system.data.local.preferences.millbilling.MillBillingCacheImpl
import com.gfs.mobile.system.data.local.preferences.user.auth.AuthenticationCache
import com.gfs.mobile.system.data.local.preferences.user.auth.AuthenticationCacheImpl
import com.gfs.mobile.system.data.local.preferences.user.previoususer.PreviousUserCache
import com.gfs.mobile.system.data.local.preferences.user.previoususer.PreviousUserCacheImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.osipxd.security.crypto.createEncrypted
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createEncrypted {
            EncryptedFile.Builder(
                context.dataStoreFile("app_pref.preferences_pb"),
                context,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
        }
    }

//    @Provides
//    @Singleton
//    fun provideDataStoreRepository(
//        dataStorePreferences: DataStore<Preferences>
//    ) = DataStoreRepository(dataStorePreferences)

    @Provides
    @Singleton
    fun provideMillBillingCache(
        dataStorePreferences: DataStore<Preferences>,
        json: Json
    ) : MillBillingCache {
        return MillBillingCacheImpl (dataStorePreferences, json)
    }

    @Provides
    @Singleton
    fun provideAuthenticationTokenCache(
        dataStorePreferences: DataStore<Preferences>,
        json: Json
    ) : AuthenticationCache {
        return AuthenticationCacheImpl (dataStorePreferences, json)
    }

    @Provides
    @Singleton
    fun providePreviousUserCache(
        dataStorePreferences: DataStore<Preferences>,
        json: Json
    ) : PreviousUserCache {
        return PreviousUserCacheImpl(dataStorePreferences, json)
    }
}