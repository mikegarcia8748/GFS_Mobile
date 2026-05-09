package com.gfs.mobile.core.domain.usecase.attendance

import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.repository.AttendanceRepository
import javax.inject.Inject

class RecordAttendanceUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    operator fun invoke(params: CreateAttendanceParams) = repository.recordAttendance(params)
}
