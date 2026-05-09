package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.model.param.AddChaffPriceParams
import com.gfs.mobile.core.domain.repository.ChaffPriceRepository
import javax.inject.Inject

class AddChaffPriceUseCase @Inject constructor(
    private val repository: ChaffPriceRepository
) {
    operator fun invoke(params: AddChaffPriceParams) = repository.addChaffPrice(params)
}
