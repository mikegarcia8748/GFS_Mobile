package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.repository.CustomerRepository
import javax.inject.Inject

class GetCustomersUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    operator fun invoke(index: Int) = repository.getCustomers(index)
}
