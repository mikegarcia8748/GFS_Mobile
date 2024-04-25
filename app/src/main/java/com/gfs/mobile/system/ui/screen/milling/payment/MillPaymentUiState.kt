package com.gfs.mobile.system.ui.screen.milling.payment

import java.math.BigDecimal

data class MillPaymentUiState(
    val amountToPay: BigDecimal? = null,
    val amountPaid: String = "",
    val formattedAmountPaid: BigDecimal = BigDecimal(0.0),
    val amountChange: BigDecimal? = null,
    val showLoadingDialog: Boolean = false,
    val transactionSave: Boolean = false,
    val showErrorMessage: Boolean = false,
    val errorMessage: String = ""
)