package com.gfs.mobile.feature.auth.ui.screen.auth

data class AuthenticationCallback(
    val onEnterPIN: (number: String) -> Unit,
    val onClickBackSpace: () -> Unit,
    val onClickSelectAccount: () -> Unit,
    val onCancelAccountSelection: () -> Unit,
    val onSelectAccount: (value: String) -> Unit
)
