package com.gfs.mobile.system.ui.screen.milling.expense.declaration

data class ExpenseDeclarationCallback(
    val onBackPressed:() -> Unit,
    val onEnterDescription: (value: String) -> Unit,
    val onEnterAmount: (value: String) -> Unit,
    val onClickSave: () -> Unit
)