package com.gfs.mobile.system.ui.screen.milling.billing

data class MillBillingCallback(
    val onBackPressed: () -> Unit,
    val onEnterCustomerName: (value: String) -> Unit,
    val onSearchCustomerName: (value: String) -> Unit,
    val onClickSearch: () -> Unit,
    val onCancelCustomerSearch: () -> Unit,
    val onEnterCustomWeight:(value: String) -> Unit,
    val onEnter60Kilos:(value: String) -> Unit,
    val onEnter50Kilos:(value: String) -> Unit,
    val onEnter30Kilos:(value: String) -> Unit,
    val onEnter25Kilos:(value: String) -> Unit,
    val onChaffWeight:(value: String) -> Unit,
    val onClickSaveBilling: () -> Unit
)