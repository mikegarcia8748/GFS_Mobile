package com.gfs.mobile.feature.auth.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.core.data.data.interceptor.AuthorizationInterceptor
import com.gfs.mobile.core.domain.usecase.auth.*
import com.gfs.mobile.core.domain.util.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val getAuthorizedUsersUseCase: GetAuthorizedUsersUseCase,
    private val authenticateMpinUseCase: AuthenticateMpinUseCase,
    private val getPreviousUserUseCase: GetPreviousUserUseCase,
    private val savePreviousUsernameUseCase: SavePreviousUsernameUseCase,
    private val saveAuthenticationTokenUseCase: SaveAuthenticationTokenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState = _uiState.asStateFlow()

    fun checkPreviousUser() {
        viewModelScope.launch {
            getPreviousUserUseCase().collect { value ->
                _uiState.update { currentState ->
                    currentState.copy(
                        userName = value.orEmpty()
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
            authenticateMpinUseCase(
                userName = uiState.value.userName.orEmpty(),
                mpin = uiState.value.userPIN
            ).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                         val data = response.data.data
                         data?.let {
                             saveAuthenticationTokenUseCase(it)
                             authorizationInterceptor.setAccessToken(data.accessToken.orEmpty())
                             savePreviousUsernameUseCase(it.userName.orEmpty())
                         }

                         _uiState.update { currentState ->
                             currentState.copy(
                                 showLoadingDialog = false,
                                 hasAuthenticated = true
                             )
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
            getAuthorizedUsersUseCase().collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false,
                                authorizeUsers = response.data.data.orEmpty(),
                                showAccountSelection = true
                            )
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
