package com.gfs.mobile.feature.ricemill.ui.screen.milling.expense.declaration

data class ExpenseDeclarationCallback(
    val onBackPressed:() -> Unit,
    val onEnterDescription: (value: String) -> Unit,
    val onEnterAmount: (value: String) -> Unit,
    val onClickBackSpace: () -> Unit,
    val onClickSave: () -> Unit
)
