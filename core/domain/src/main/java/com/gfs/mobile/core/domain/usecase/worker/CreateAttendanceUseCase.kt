package com.gfs.mobile.core.domain.usecase.worker

import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.repository.AttendanceRepository
import javax.inject.Inject

class CreateAttendanceUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    operator fun invoke(params: CreateAttendanceParams) = repository.createAttendance(params)
}
