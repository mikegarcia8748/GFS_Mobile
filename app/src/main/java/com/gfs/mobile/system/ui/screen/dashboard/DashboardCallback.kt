package com.gfs.mobile.system.ui.screen.dashboard

data class DashboardCallback(
    val onClickMillBilling: () -> Unit,
    val onClickDashboardItem: (index: Int) -> Unit
)