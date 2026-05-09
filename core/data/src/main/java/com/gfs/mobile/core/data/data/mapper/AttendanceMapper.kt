package com.gfs.mobile.core.data.data.mapper

import com.gfs.mobile.core.data.data.local.room.attendance.AttendanceEntity
import com.gfs.mobile.core.domain.model.attendance.AttendanceRecord
import com.gfs.mobile.core.domain.model.attendance.AttendanceStatus

fun AttendanceEntity.toDomain(): AttendanceRecord {
    return AttendanceRecord(
        attendanceID = attendanceID,
        workerID = workerID,
        businessLineID = businessLineID,
        date = date,
        status = AttendanceStatus.valueOf(status),
        entryBy = entryBy,
        timestamp = timestamp
    )
}

fun AttendanceRecord.toEntity(isSynced: Boolean = false): AttendanceEntity {
    return AttendanceEntity(
        attendanceID = attendanceID,
        workerID = workerID,
        businessLineID = businessLineID,
        date = date,
        status = status.name,
        entryBy = entryBy,
        timestamp = timestamp,
        isSynced = isSynced
    )
}
