package com.gfs.mobile.feature.auth.ui.screen.auth

import com.gfs.mobile.core.domain.model.authorizeusers.AuthorizeUsers

data class AuthenticationUiState(
    val authorizeUsers: List<AuthorizeUsers> = emptyList(),
    val userName: String? = null,
    val showAccountSelection: Boolean = false,
    val userPIN: String = "",
    val hasSixDigit: Boolean = false,
    val firstDigit: Boolean = false,
    val secondDigit: Boolean = false,
    val thirdDigit: Boolean = false,
    val fourthDigit: Boolean = false,
    val fifthDigit: Boolean = false,
    val sixthDigit: Boolean = false,
    val email: String = "",
    val pass: String = "",
    val isEmailLogin: Boolean = false,
    val errorMessage: String? = null,
    val hasAuthenticated: Boolean = false,
    val showLoadingDialog: Boolean = false
)
