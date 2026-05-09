package com.gfs.mobile.core.domain.usecase.auth

import com.gfs.mobile.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SavePreviousUsernameUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(value: String) = repository.savePreviousUsername(value)
}
