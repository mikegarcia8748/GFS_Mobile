package com.gfs.mobile.system.ui.screen.milling.payroll

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MillWorkerPayrollViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(MillWorkerPayrollUiState())
    val uiState = _uiState.asStateFlow()

}