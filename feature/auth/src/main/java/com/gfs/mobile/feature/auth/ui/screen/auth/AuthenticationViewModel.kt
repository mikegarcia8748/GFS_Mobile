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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val getAuthorizedUsersUseCase: GetAuthorizedUsersUseCase,
    private val authenticateMpinUseCase: AuthenticateMpinUseCase,
    private val signInWithCustomTokenUseCase: SignInWithCustomTokenUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
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
        if (uiState.value.userPIN.length >= 6) return // SECURITY-05: Length bound
        
        _uiState.update { currentState ->
            currentState.copy(
                userPIN = uiState.value.userPIN + value,
                hasSixDigit = (uiState.value.userPIN + value).length == 6
            )
        }
    }

    fun setBackSpaceAction() {
        _uiState.update { currentState ->
            currentState.copy(
                userPIN = uiState.value.userPIN.dropLast(1),
                hasSixDigit = false
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

    fun toggleLoginMode() {
        _uiState.update { it.copy(isEmailLogin = !it.isEmailLogin) }
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(pass = value) }
    }

    fun authenticateWithEmail() {
        val email = uiState.value.email
        val password = uiState.value.pass

        // SECURITY-05: Input Validation
        if (email.isBlank() || password.length < 8) {
            _uiState.update { it.copy(errorMessage = "Invalid email or password.") }
            return
        }

        Timber.d("Starting email authentication for: $email")

        viewModelScope.launch {
            signInWithEmailUseCase(email, password).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        Timber.i("Email authentication successful")
                        _uiState.update { it.copy(
                            showLoadingDialog = false,
                            hasAuthenticated = true
                        ) }
                    }
                    is NetworkResource.Loading -> {
                        _uiState.update { it.copy(showLoadingDialog = true) }
                    }
                    is NetworkResource.Error -> {
                        Timber.w("Email authentication failed: ${response.error?.message}")
                        _uiState.update { it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Invalid email or password." // SECURITY-15: Generic
                        ) }
                    }
                }
            }
        }
    }

    fun authenticateUser() {
        val userName = uiState.value.userName
        val mpin = uiState.value.userPIN

        // SECURITY-05: Input Validation
        if (userName.isNullOrBlank() || mpin.length != 6) {
            _uiState.update { it.copy(errorMessage = "Invalid login credentials.") }
            return
        }

        Timber.d("Starting authentication for user: $userName") // SECURITY-03: Structured Logging

        viewModelScope.launch {
            authenticateMpinUseCase(
                userName = userName,
                mpin = mpin
            ).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        val data = response.data.data
                        val token = data?.accessToken
                        if (data != null && !token.isNullOrEmpty()) {
                            Timber.i("MPIN authentication successful for $userName")
                            
                            // Step 2: Sign in with Firebase using the custom token
                            signInWithFirebase(token, data)
                        } else {
                            Timber.e("Authentication successful but token is missing for $userName")
                            _uiState.update { it.copy(
                                showLoadingDialog = false,
                                errorMessage = "Authentication failed. Please try again." // SECURITY-15: Generic error
                            ) }
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

                    is NetworkResource.Error -> {
                        Timber.w("MPIN authentication failed for $userName: ${response.error?.message}")
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false,
                                errorMessage = "Invalid MPIN or account." // SECURITY-15: Generic error
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun signInWithFirebase(token: String, authModel: com.gfs.mobile.core.domain.model.authentication.AuthenticationMPINModel) {
        signInWithCustomTokenUseCase(token).collect { response ->
            when (response) {
                is NetworkResource.Success -> {
                    Timber.i("Firebase authentication successful")
                    saveAuthenticationTokenUseCase(authModel)
                    authorizationInterceptor.setAccessToken(authModel.accessToken.orEmpty())
                    savePreviousUsernameUseCase(authModel.userName.orEmpty())

                    _uiState.update { currentState ->
                        currentState.copy(
                            showLoadingDialog = false,
                            hasAuthenticated = true
                        )
                    }
                }
                is NetworkResource.Error -> {
                    Timber.e("Firebase authentication failed: ${response.error?.message}")
                    _uiState.update { currentState ->
                        currentState.copy(
                            showLoadingDialog = false,
                            errorMessage = "System error during authentication." // SECURITY-15: Generic error
                        )
                    }
                }
                is NetworkResource.Loading -> {
                    // Already showing loading
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
                        Timber.e("Failed to fetch authorized users: ${response.error?.message}")
                        _uiState.update { currentState ->
                            currentState.copy(
                                showLoadingDialog = false,
                                errorMessage = "Unable to load accounts."
                            )
                        }
                    }
                }
            }
        }
    }
}
