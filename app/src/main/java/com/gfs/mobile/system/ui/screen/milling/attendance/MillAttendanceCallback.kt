package com.gfs.mobile.system.ui.screen.milling.attendance


data class MillAttendanceCallback(
    val onBackPressed: () -> Unit,
    val onClickPresent: (uid: String) -> Unit,
    val onClickAbsent: (uid: String) -> Unit,
)