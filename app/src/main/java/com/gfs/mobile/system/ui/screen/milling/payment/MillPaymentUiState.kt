package com.gfs.mobile.system.ui.screen.milling.payment

import com.gfs.mobile.system.data.param.MillTransactionParams
import java.math.BigDecimal

data class MillPaymentUiState(
    val millTransactionParams: MillTransactionParams? = null,
    val amountToPay: BigDecimal? = null,
    val amountPaid: String = "",
    val formattedAmountPaid: BigDecimal = BigDecimal(0.0),
    val amountChange: BigDecimal? = null,
    val balance:  BigDecimal? = null,
    val showLoadingDialog: Boolean = false,
    val transactionSave: Boolean = false,
    val errorMessage: String = ""
)