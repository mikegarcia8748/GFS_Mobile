package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.model.param.AddMillPriceParams
import com.gfs.mobile.core.domain.repository.MillPriceRepository
import javax.inject.Inject

class AddMillPriceUseCase @Inject constructor(
    private val repository: MillPriceRepository
) {
    operator fun invoke(params: AddMillPriceParams) = repository.addMillPrice(params)
}
