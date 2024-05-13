package com.gfs.mobile.system.ui.screen.milling.worker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MillWorkerViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(MillWorkerUiState())
    val uiState = _uiState.asStateFlow()
}