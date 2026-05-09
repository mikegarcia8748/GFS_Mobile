package com.gfs.mobile.core.domain.usecase.worker

import com.gfs.mobile.core.domain.repository.AttendanceRepository
import javax.inject.Inject

class GetAttendanceTodayUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    operator fun invoke() = repository.getAttendanceToday()
}
