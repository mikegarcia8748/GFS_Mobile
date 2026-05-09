package com.gfs.mobile.core.domain.usecase.auth

import com.gfs.mobile.core.domain.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SaveAuthenticationTokenUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(value: AuthenticationMPINModel) = repository.saveAuthenticationToken(value)
}
