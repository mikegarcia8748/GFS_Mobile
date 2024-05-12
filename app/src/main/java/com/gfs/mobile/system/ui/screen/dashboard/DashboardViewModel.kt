package com.gfs.mobile.system.ui.screen.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAuthenticationToken().collect { content ->
                _uiState.update {  currentState ->
                    currentState.copy(
                        userName = content?.fullName.orEmpty()
                    )
                }
            }
        }
    }
}