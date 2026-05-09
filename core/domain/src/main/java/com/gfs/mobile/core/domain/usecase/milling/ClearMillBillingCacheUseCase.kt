package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.repository.MillTransactionRepository
import javax.inject.Inject

class ClearMillBillingCacheUseCase @Inject constructor(
    private val repository: MillTransactionRepository
) {
    suspend operator fun invoke() = repository.clearMillBillingCache()
}
