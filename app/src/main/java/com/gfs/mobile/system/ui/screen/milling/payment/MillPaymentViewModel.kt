package com.gfs.mobile.system.ui.screen.milling.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.local.preferences.millbilling.MillBillingCache
import com.gfs.mobile.system.extensions.convertAmountToBigDecimal
import com.gfs.mobile.system.extensions.formatAmountWithCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MillPaymentViewModel @Inject constructor(
    private val millBillingCache: MillBillingCache
) : ViewModel() {

     private val _uiState = MutableStateFlow(MillPaymentUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTransactionDetail()
    }

    private fun getTransactionDetail() {
        viewModelScope.launch {
            millBillingCache.getMillBilling().collect{ value ->
                _uiState.update { currentState ->
                    currentState.copy(
                        amountToPay = value?.total?.toBigDecimal()
                    )
                }
            }
        }
    }

    fun setInputAmount(value: String) {
        val stringValue = uiState.value.amountPaid
        // validate if a period already exist in the input amount...
        if (stringValue.isNotEmpty() &&
            stringValue.last().toString() == "." &&
            value == ".") {
            return
        }

        val newValue = "$stringValue${value}"

        _uiState.update { currentState ->
            currentState.copy(
                amountPaid = newValue,
                formattedAmountPaid = newValue.convertAmountToBigDecimal()
            )
        }
        calculateChange()
    }

    fun clearInputAmount() {
        val stringValue = uiState.value.amountPaid
        val newValue = stringValue.dropLast(1)
        _uiState.update { currentState ->
            currentState.copy(
                amountPaid = newValue,
                formattedAmountPaid = newValue.convertAmountToBigDecimal()
            )
        }
        calculateChange()
    }

    private fun calculateChange() {
        val billAmount = uiState.value.amountToPay
        val amountPaid = uiState.value.formattedAmountPaid
        val amountChange = amountPaid.minus(billAmount?: BigDecimal(0.0))
        _uiState.update { currentState ->
            currentState.copy(
                amountChange = amountChange
            )
        }
    }

    fun saveTransaction() {
        _uiState.update { currentState ->
            currentState.copy(
                transactionSave = true
            )
        }
    }
    
    fun dismissSuccessDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                transactionSave = false
            )
        }
    }
}