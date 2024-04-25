package com.gfs.mobile.system.ui.screen.milling.attendance

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MillAttendanceViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(MillAttendanceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        importWorkers()
    }

    private fun importWorkers() {

    }

}