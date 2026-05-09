package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.model.param.MillTransactionParams
import com.gfs.mobile.core.domain.repository.MillTransactionRepository
import javax.inject.Inject

class SaveMillBillingCacheUseCase @Inject constructor(
    private val repository: MillTransactionRepository
) {
    suspend operator fun invoke(params: MillTransactionParams) = repository.saveMillBillingToCache(params)
}
