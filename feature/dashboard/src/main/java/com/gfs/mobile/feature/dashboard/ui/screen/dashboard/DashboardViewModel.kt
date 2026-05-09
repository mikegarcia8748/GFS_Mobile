package com.gfs.mobile.feature.dashboard.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.core.domain.usecase.auth.GetAuthenticationTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAuthenticationTokenUseCase: GetAuthenticationTokenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAuthenticationTokenUseCase().collect { content ->
                _uiState.update {  currentState ->
                    currentState.copy(
                        userName = content?.fullName.orEmpty()
                    )
                }
            }
        }
    }
}
