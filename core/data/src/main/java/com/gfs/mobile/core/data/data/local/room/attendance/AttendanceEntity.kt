package com.gfs.mobile.core.data.data.local.room.attendance

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attendance",
    indices = [Index(value = ["workerID", "date", "businessLineID"], unique = true)]
)
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val attendanceID: String? = null,
    val workerID: String,
    val businessLineID: String,
    val date: String,
    val status: String,
    val entryBy: String,
    val timestamp: Long,
    val isSynced: Boolean = false
)
