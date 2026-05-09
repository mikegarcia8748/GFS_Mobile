package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.model.param.AddCustomerParams
import com.gfs.mobile.core.domain.repository.CustomerRepository
import javax.inject.Inject

class AddCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    operator fun invoke(params: AddCustomerParams) = repository.addCustomer(params)
}
