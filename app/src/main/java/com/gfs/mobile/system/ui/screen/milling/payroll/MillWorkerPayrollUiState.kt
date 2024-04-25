package com.gfs.mobile.system.ui.screen.milling.payroll

import com.gfs.mobile.system.data.model.payroll.WorkerPayrollDetail

data class MillWorkerPayrollUiState(
    val loadingPayroll: Boolean = false,
    val workerPayrollDetail: List<WorkerPayrollDetail>? = emptyList()
)