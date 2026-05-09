package com.gfs.mobile.core.domain.model.attendance

import kotlinx.serialization.Serializable

@Serializable
data class AttendanceSummary(
    val workerID: String,
    val fullName: String,
    val userName: String,
    val entries: List<AttendanceRecord> = emptyList(),
    val overallStatus: AttendanceStatus? = null
)
