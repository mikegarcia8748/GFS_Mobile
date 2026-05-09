package com.gfs.mobile.feature.ricemill.ui.screen.milling.attendance

import com.gfs.mobile.core.domain.model.AttendanceTodayModel

data class MillAttendanceUiState(
    val currentUser: String = "",
    val loadingWorkers: Boolean = false,
    val workerList: List<AttendanceTodayModel>? = null,
    val loadingWorkerError: String? = null,
    val showLoadingDialog: Boolean = false,
    val errorMessage: String? = null,
    val currentDate: String = "April 24, 2024",
)
