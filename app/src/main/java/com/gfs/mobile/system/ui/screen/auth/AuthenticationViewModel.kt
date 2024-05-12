package com.gfs.mobile.system.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.interceptor.AuthorizationInterceptor
import com.gfs.mobile.system.data.local.room.PreferenceResource
import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.system.data.remote.NetworkResource
import com.gfs.mobile.system.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState = _uiState.asStateFlow()

    fun checkPreviousUser() {
        viewModelScope.launch {
            repository.getPreviousUser().collect { value ->
                _uiState.update { currentState ->
                    currentState.copy(
                        userName = value.data.orEmpty()
                    )
                }
            }
        }
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
                userName = value,
                showAccountSelection = false
            )
        }
    }

    fun accountSelectionCanceled() {
        _uiState.update { currentState ->
            currentState.copy(
                showAccountSelection = false
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
                userName = uiState.value.userName.orEmpty(),
                mpin = uiState.value.userPIN
            ).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                         when (response.data?.status) {
                             "success" -> {

                                 val data = response.data.data
                                 data?.let {
                                     repository.saveAuthenticationToken(it).collect {
                                         authorizationInterceptor.setAccessToken(data.accessToken.orEmpty())
                                         Timber.d("Authentication info saved!")
                                     }

                                     repository.savePreviousUsername(it.userName.orEmpty()).collect { }
                                 }

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

                    is NetworkResource.Loading -> {
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

    fun getAuthorizeUsers() {
        viewModelScope.launch {
            repository.getAuthorizeUsers().collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        showLoadingDialog = false,
                                        authorizeUsers = response.data.data.orEmpty(),
                                        showAccountSelection = true
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
                        val error = response.error
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false,
                                errorMessage = error?.message
                            )
                        }
                    }
                }
            }
        }
    }
}