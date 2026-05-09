package com.gfs.mobile.feature.payroll.ui.screen.attendance

import com.gfs.mobile.core.domain.model.attendance.AttendanceSummary
import com.gfs.mobile.core.domain.model.business.BusinessLine

data class AttendanceUiState(
    val currentBusinessLine: BusinessLine = BusinessLine.MILL,
    val summaries: List<AttendanceSummary> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showLoadingDialog: Boolean = false,
    val currentUser: String = ""
)
