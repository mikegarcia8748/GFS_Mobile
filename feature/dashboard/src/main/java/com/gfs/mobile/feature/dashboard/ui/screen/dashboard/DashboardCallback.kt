package com.gfs.mobile.feature.dashboard.ui.screen.dashboard

data class DashboardCallback(
    val onClickMillBilling: () -> Unit,
    val onClickDashboardItem: (index: Int) -> Unit
)
