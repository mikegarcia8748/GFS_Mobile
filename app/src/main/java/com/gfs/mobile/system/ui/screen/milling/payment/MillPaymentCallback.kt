package com.gfs.mobile.system.ui.screen.milling.payment

data class MillPaymentCallback(
    val onBackPressed: () -> Unit,
    val onEnterAmountPaid: (value: String) -> Unit,
    val onClickBackSpace: () -> Unit,
    val onSaveTransaction: () -> Unit
)