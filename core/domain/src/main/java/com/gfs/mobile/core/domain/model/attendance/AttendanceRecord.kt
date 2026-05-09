package com.gfs.mobile.core.domain.model.attendance

import kotlinx.serialization.Serializable

@Serializable
data class AttendanceRecord(
    val attendanceID: String? = null,
    val workerID: String,
    val businessLineID: String,
    val date: String,
    val status: AttendanceStatus,
    val entryBy: String,
    val timestamp: Long
)
