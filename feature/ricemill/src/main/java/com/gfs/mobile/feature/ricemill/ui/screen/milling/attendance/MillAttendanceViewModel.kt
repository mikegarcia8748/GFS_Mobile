package com.gfs.mobile.feature.ricemill.ui.screen.milling.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.core.domain.model.param.CreateAttendanceParams
import com.gfs.mobile.core.domain.usecase.auth.GetAuthenticationTokenUseCase
import com.gfs.mobile.core.domain.usecase.worker.CreateAttendanceUseCase
import com.gfs.mobile.core.domain.usecase.worker.GetAttendanceTodayUseCase
import com.gfs.mobile.core.domain.util.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MillAttendanceViewModel @Inject constructor(
    private val getAuthenticationTokenUseCase: GetAuthenticationTokenUseCase,
    private val getAttendanceTodayUseCase: GetAttendanceTodayUseCase,
    private val createAttendanceUseCase: CreateAttendanceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MillAttendanceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initiateCurrentUser()
        getAttendanceToday()
    }

    private fun initiateCurrentUser() {
        viewModelScope.launch {
            getAuthenticationTokenUseCase().collect { value ->
                _uiState.update { currentState ->
                    currentState.copy(
                        currentUser = value?.userID.orEmpty()
                    )
                }
            }
        }
    }

    private fun getAttendanceToday() {
        viewModelScope.launch {
            getAttendanceTodayUseCase().collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        val data = response.data.data

                        _uiState.update { currentState ->
                            currentState.copy(
                                loadingWorkers = false,
                                workerList = data
                            )
                        }
                    }

                    is NetworkResource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                loadingWorkers = true
                            )
                        }
                    }

                    else -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                loadingWorkers = false,
                                loadingWorkerError = response.error?.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun tagAsPresent(workerID: String) {
        viewModelScope.launch {
            val params = CreateAttendanceParams(
                entryBy = _uiState.value.currentUser,
                workerID = workerID
            )
            createAttendanceUseCase(params).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false
                            )
                        }
                        getAttendanceToday()
                    }

                    is NetworkResource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = true
                            )
                        }
                    }

                    else -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false,
                                errorMessage = response.error?.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun tagAsAbsent(workerID: String) {
        viewModelScope.launch {
            val params = CreateAttendanceParams(
                entryBy = _uiState.value.currentUser,
                workerID = workerID
            )
            createAttendanceUseCase(params).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false
                            )
                        }
                        getAttendanceToday()
                    }

                    is NetworkResource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = true
                            )
                        }
                    }

                    else -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false,
                                errorMessage = response.error?.message
                            )
                        }
                    }
                }
            }
        }
    }
}
