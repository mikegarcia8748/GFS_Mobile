package com.gfs.mobile.system.ui.screen.milling.attendance

import com.gfs.mobile.system.data.model.AttendanceTodayModel

data class MillAttendanceUiState(
    val currentUser: String = "",
    val loadingWorkers: Boolean = false,
    val workerList: List<AttendanceTodayModel>? = null,
    val loadingWorkerError: String? = null,
    val showLoadingDialog: Boolean = false,
    val errorMessage: String? = null,
    val currentDate: String = "April 24, 2024",
)