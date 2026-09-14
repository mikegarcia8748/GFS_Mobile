package com.gfs.mobile.system.ui.screen.dashboard

data class DashboardCallback(
    val onClickMillBilling: () -> Unit,
    val onClickInventory: () -> Unit,
    val onClickAttendance: () -> Unit,
    val onClickPayroll: () -> Unit,
    val onClickSettings: () -> Unit,
    val onClickCustomer: () -> Unit,
)