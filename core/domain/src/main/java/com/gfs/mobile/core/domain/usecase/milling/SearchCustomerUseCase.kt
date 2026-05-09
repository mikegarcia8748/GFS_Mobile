package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.repository.CustomerRepository
import javax.inject.Inject

class SearchCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    operator fun invoke(name: String) = repository.searchCustomer(name)
}
