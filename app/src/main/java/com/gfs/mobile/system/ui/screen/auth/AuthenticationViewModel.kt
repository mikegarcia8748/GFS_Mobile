package com.gfs.mobile.system.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState = _uiState.asStateFlow()

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

    fun authenticateUser() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    hasSixDigit = false,
                    showLoadingDialog = true
                )
            }

            var timer = 0
            while (timer < 2) {
                Log.d("Sample", "Time: $timer")
                delay(1000)
                timer++
            }

            _uiState.update { currentState ->
                currentState.copy(
                    showLoadingDialog = false,
                    hasAuthenticated = true
                )
            }
        }
    }
}