package com.gfs.mobile.core.domain.usecase.milling

import com.gfs.mobile.core.domain.repository.ChaffPriceRepository
import javax.inject.Inject

class GetChaffPriceUseCase @Inject constructor(
    private val repository: ChaffPriceRepository
) {
    operator fun invoke() = repository.getChaffPrice()
}
