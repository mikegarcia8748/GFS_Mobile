package com.gfs.mobile.system.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.remote.Resource
import com.gfs.mobile.system.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAuthorizeUsers()
    }

    fun setUserInput(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userPIN = uiState.value.userPIN + value,
                hasSixDigit = uiState.value.userPIN.length > 4
            )
        }
    }

    fun setBackSpaceAction() {
        _uiState.update { currentState ->
            currentState.copy(
                userPIN = uiState.value.userPIN.dropLast(1)
            )
        }
    }

    fun setActiveAccount(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userName = value
            )
        }
    }

    fun dismissErrorDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = null
            )
        }
    }

    fun authenticateUser() {
        viewModelScope.launch {
            repository.authenticateMPIN(
                userName = uiState.value.userName,
                mpin = uiState.value.userPIN
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                         when (response.data?.status) {
                             "success" -> {
                                 _uiState.update { currentState ->
                                     currentState.copy(
                                         showLoadingDialog = false,
                                         hasAuthenticated = true
                                     )
                                 }
                             }

                             else -> {
                                 _uiState.update { currentState ->
                                     currentState.copy(
                                         showLoadingDialog = false,
                                         errorMessage = response.data?.message.orEmpty()
                                     )
                                 }
                             }
                         }
                    }

                    is Resource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                userPIN = "",
                                hasSixDigit = false,
                                showLoadingDialog = true
                            )
                        }
                    }

                    else -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false,
                                errorMessage = response.error?.message.orEmpty()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAuthorizeUsers() {
        viewModelScope.launch {
            repository.getAuthorizeUsers().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        when (response.data?.status) {
                            "success" -> {
                                Timber.d("Authorize accounts loaded from the server!")
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        authorizeUsers = response.data.data.orEmpty()
                                    )
                                }
                            }

                            else -> {
                                Timber.d("An error occurred while loading authorize accounts!")
                            }
                        }
                    }

                    is Resource.Loading -> {
                        Timber.d("Loading authorize accounts...")
                    }

                    else -> {
                        val error = response.error
                        Timber.d("An error occurred while loading authorize accounts! \nError message: $error")
                    }
                }
            }
        }
    }
}