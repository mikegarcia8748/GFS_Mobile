package com.gfs.mobile.core.domain.usecase.worker

import com.gfs.mobile.core.domain.repository.WorkerRepository
import javax.inject.Inject

class GetEmployeesUseCase @Inject constructor(
    private val repository: WorkerRepository
) {
    operator fun invoke() = repository.getEmployees()
}
