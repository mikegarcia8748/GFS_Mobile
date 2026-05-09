package com.gfs.mobile.core.data.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gfs.mobile.core.data.data.local.room.attendance.AttendanceDao
import com.gfs.mobile.core.data.data.local.room.attendance.AttendanceEntity
import com.gfs.mobile.core.data.data.local.room.customer.CustomerEntity
import com.gfs.mobile.core.data.data.local.room.customer.CustomerDao

@Database(
    entities = [
        CustomerEntity::class,
        AttendanceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun attendanceDao(): AttendanceDao
    abstract fun customerDao(): CustomerDao
}
