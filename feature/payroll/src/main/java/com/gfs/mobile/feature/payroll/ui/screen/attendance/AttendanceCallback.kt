package com.gfs.mobile.feature.payroll.ui.screen.attendance

import com.gfs.mobile.core.domain.model.attendance.AttendanceStatus
import com.gfs.mobile.core.domain.model.business.BusinessLine

data class AttendanceCallback(
    val onBackPressed: () -> Unit,
    val onBusinessLineSelected: (BusinessLine) -> Unit,
    val onMarkStatus: (workerId: String, status: AttendanceStatus) -> Unit,
    val onDismissError: () -> Unit
)
