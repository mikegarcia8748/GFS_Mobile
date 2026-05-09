package com.gfs.mobile.core.domain.usecase.auth

import com.gfs.mobile.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticateMpinUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(userName: String, mpin: String) = repository.authenticateMPIN(userName, mpin)
}
