package com.gfs.mobile.feature.ricemill.ui.screen.milling.payroll

import com.gfs.mobile.core.domain.model.payroll.WorkerPayrollDetail

data class MillWorkerPayrollUiState(
    val loadingPayroll: Boolean = false,
    val workerPayrollDetail: List<WorkerPayrollDetail>? = emptyList()
)
