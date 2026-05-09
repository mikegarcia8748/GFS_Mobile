package com.gfs.mobile.core.data.di

import android.content.Context
import androidx.room.Room
import com.gfs.mobile.core.data.data.local.room.RoomDB
import com.gfs.mobile.core.data.data.local.room.attendance.AttendanceDao
import com.gfs.mobile.core.data.data.local.room.customer.CustomerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDB {
        return Room.databaseBuilder(
            context,
            RoomDB::class.java,
            "gfs_mobile_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAttendanceDao(db: RoomDB): AttendanceDao {
        return db.attendanceDao()
    }

    @Provides
    @Singleton
    fun provideCustomerDao(db: RoomDB): CustomerDao {
        return db.customerDao()
    }
}
