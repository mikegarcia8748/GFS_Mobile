package com.gfs.mobile.core.data.di

import com.gfs.mobile.core.domain.repository.*
import com.gfs.mobile.core.data.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindWorkerRepository(
        workerRepositoryImpl: WorkerRepositoryImpl
    ): WorkerRepository

    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        customerRepositoryImpl: CustomerRepositoryImpl
    ): CustomerRepository

    @Binds
    @Singleton
    abstract fun bindMillPriceRepository(
        millPriceRepositoryImpl: MillPriceRepositoryImpl
    ): MillPriceRepository

    @Binds
    @Singleton
    abstract fun bindChaffPriceRepository(
        chaffPriceRepositoryImpl: ChaffPriceRepositoryImpl
    ): ChaffPriceRepository

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl
    ): AttendanceRepository

    @Binds
    @Singleton
    abstract fun bindMillTransactionRepository(
        millTransactionRepositoryImpl: MillTransactionRepositoryImpl
    ): MillTransactionRepository
}
