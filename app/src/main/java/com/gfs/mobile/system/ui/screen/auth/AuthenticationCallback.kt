package com.gfs.mobile.system.ui.screen.auth

data class AuthenticationCallback(
    val onEnterPIN: (number: String) -> Unit,
    val onClickBackSpace: () -> Unit,
    val onSelectAccount: (value: String) -> Unit
)