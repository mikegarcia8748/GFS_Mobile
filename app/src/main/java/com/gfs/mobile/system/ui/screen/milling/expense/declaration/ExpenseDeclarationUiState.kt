package com.gfs.mobile.system.ui.screen.milling.expense.declaration

import java.math.BigDecimal

data class ExpenseDeclarationUiState(
    val showLoadingDialog: Boolean = false,
    val description: String = "",
    val amount: String = "",
    val formattedAmount: BigDecimal = BigDecimal(0.0)
)