package com.gfs.mobile.system.ui.screen.milling.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.local.preferences.user.auth.AuthenticationCache
import com.gfs.mobile.system.data.param.CreateAttendanceParams
import com.gfs.mobile.system.data.remote.NetworkResource
import com.gfs.mobile.system.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MillAttendanceViewModel @Inject constructor(
    private val authorizeUserCache: AuthenticationCache,
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MillAttendanceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initiateCurrentUser()
        getAttendanceToday()
    }

    private fun initiateCurrentUser() {
        viewModelScope.launch {
            authorizeUserCache.getAuthenticationCache().collect { value ->
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
            repository.getAttendanceToday().collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {

                                val data = response.data.data

                                _uiState.update { currentState ->
                                    currentState.copy(
                                        loadingWorkers = false,
                                        workerList = data
                                    )
                                }
                            }

                            else -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        loadingWorkers = false,
                                        loadingWorkerError = response.data?.message
                                    )
                                }
                            }
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
            repository.createAttendance(params).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        showLoadingDialog = false
                                    )
                                }
                            }

                            else -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        showLoadingDialog = false,
                                        errorMessage = response.data?.message
                                    )
                                }
                            }
                        }
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

        getAttendanceToday()
    }

    fun tagAsAbsent(workerID: String) {
        viewModelScope.launch {
            val params = CreateAttendanceParams(
                entryBy = _uiState.value.currentUser,
                workerID = workerID
            )
            repository.createAttendance(params).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        showLoadingDialog = false
                                    )
                                }
                            }

                            else -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        showLoadingDialog = false,
                                        errorMessage = response.data?.message
                                    )
                                }
                            }
                        }
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

        getAttendanceToday()
    }
}