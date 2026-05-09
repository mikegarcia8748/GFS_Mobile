package com.gfs.mobile.feature.ricemill.ui.screen.milling.billing

import com.gfs.mobile.core.domain.model.customer.CustomerModel

data class MillBillingCallback(
    val onBackPressed: () -> Unit,
    val onSelectCustomer: (customer: CustomerModel) -> Unit,
    val onSearchCustomerName: (value: String) -> Unit,
    val onClickSearch: () -> Unit,
    val onCancelCustomerSearch: () -> Unit,
    val onAddCustomer: (name: String, alias: String) -> Unit,
    val onEnterCustomWeight:(value: String) -> Unit,
    val onEnter60Kilos:(value: String) -> Unit,
    val onEnter50Kilos:(value: String) -> Unit,
    val onEnter30Kilos:(value: String) -> Unit,
    val onEnter25Kilos:(value: String) -> Unit,
    val onChaffWeight:(value: String) -> Unit,
    val onClickSaveBilling: () -> Unit
)
