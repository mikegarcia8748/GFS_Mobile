package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.model.param.MillTransactionParams
import com.gfs.mobile.core.domain.repository.MillTransactionRepository
import javax.inject.Inject

class SaveMillTransactionUseCase @Inject constructor(
    private val repository: MillTransactionRepository
) {
    operator fun invoke(params: MillTransactionParams) = repository.saveMillTransaction(params)
}
