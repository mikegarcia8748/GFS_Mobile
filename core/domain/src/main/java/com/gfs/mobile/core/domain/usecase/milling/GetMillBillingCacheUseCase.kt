package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.repository.MillTransactionRepository
import javax.inject.Inject

class GetMillBillingCacheUseCase @Inject constructor(
    private val repository: MillTransactionRepository
) {
    operator fun invoke() = repository.getMillBillingCache()
}
