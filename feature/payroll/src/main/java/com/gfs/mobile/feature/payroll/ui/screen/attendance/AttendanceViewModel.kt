package com.gfs.mobile.feature.payroll.ui.screen.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.core.domain.model.attendance.AttendanceStatus
import com.gfs.mobile.core.domain.model.business.BusinessLine
import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.usecase.attendance.GetConsolidatedAttendanceUseCase
import com.gfs.mobile.core.domain.usecase.attendance.RecordAttendanceUseCase
import com.gfs.mobile.core.domain.usecase.auth.GetAuthenticationTokenUseCase
import com.gfs.mobile.core.domain.util.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val getAuthenticationTokenUseCase: GetAuthenticationTokenUseCase,
    private val getConsolidatedAttendanceUseCase: GetConsolidatedAttendanceUseCase,
    private val recordAttendanceUseCase: RecordAttendanceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initiateCurrentUser()
        refreshAttendance()
    }

    private fun initiateCurrentUser() {
        viewModelScope.launch {
            getAuthenticationTokenUseCase().collect { value ->
                _uiState.update { it.copy(currentUser = value?.userID.orEmpty()) }
            }
        }
    }

    fun refreshAttendance() {
        viewModelScope.launch {
            getConsolidatedAttendanceUseCase().collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        _uiState.update { it.copy(isLoading = false, summaries = response.data ?: emptyList()) }
                    }
                    is NetworkResource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is NetworkResource.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = response.error?.message) }
                    }
                }
            }
        }
    }

    fun setBusinessLine(businessLine: BusinessLine) {
        _uiState.update { it.copy(currentBusinessLine = businessLine) }
    }

    fun markStatus(workerId: String, status: AttendanceStatus) {
        viewModelScope.launch {
            val params = CreateAttendanceParams(
                workerID = workerId,
                entryBy = _uiState.value.currentUser,
                businessLineID = _uiState.value.currentBusinessLine.id,
                status = status.name
            )
            recordAttendanceUseCase(params).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        _uiState.update { it.copy(showLoadingDialog = false) }
                        refreshAttendance()
                    }
                    is NetworkResource.Loading -> {
                        _uiState.update { it.copy(showLoadingDialog = true) }
                    }
                    is NetworkResource.Error -> {
                        _uiState.update { it.copy(showLoadingDialog = false, errorMessage = response.error?.message) }
                    }
                }
            }
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
