package com.gfs.mobile.core.domain.usecase.auth

import com.gfs.mobile.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetAuthorizedUsersUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke() = repository.getAuthorizeUsers()
}
