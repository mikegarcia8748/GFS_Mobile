package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.repository.MillPriceRepository
import javax.inject.Inject

class GetMillPriceUseCase @Inject constructor(
    private val repository: MillPriceRepository
) {
    operator fun invoke() = repository.getMillPrice()
}
