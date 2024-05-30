package com.gfs.mobile.system.ui.screen.milling.expense.declaration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.extensions.convertAmountToBigDecimal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseDeclarationViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseDeclarationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEnterDescription(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = value
            )
        }
    }

    fun onEnterAmount(value: String) {
        val stringValue = uiState.value.amount

        if (stringValue.isNotEmpty() &&
            stringValue.last().toString() == "." &&
            value == ".") {
            return
        }

        val newValue = "$stringValue${value}"

        _uiState.update { currentState ->
            currentState.copy(
                amount = newValue,
                formattedAmount = newValue.convertAmountToBigDecimal()
            )
        }
    }

    fun saveExpenseDetail() {
        viewModelScope.launch {

        }
    }
}