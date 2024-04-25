package com.gfs.mobile.system.ui.screen.auth

data class AuthenticationUiState(
    val userPIN: String = "",
    val hasSixDigit: Boolean = false,
    val firstDigit: Boolean = false,
    val secondDigit: Boolean = false,
    val thirdDigit: Boolean = false,
    val fourthDigit: Boolean = false,
    val fifthDigit: Boolean = false,
    val sixthDigit: Boolean = false,
    val errorMessage: String? = null,
    val hasAuthenticated: Boolean = false,
    val showLoadingDialog: Boolean = false
)