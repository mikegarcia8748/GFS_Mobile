package com.gfs.mobile.core.domain.usecase.attendance

import com.gfs.mobile.core.domain.repository.AttendanceRepository
import javax.inject.Inject

class SyncAttendanceUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    operator fun invoke() = repository.syncUnsyncedRecords()
}
