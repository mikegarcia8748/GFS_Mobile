package com.gfs.mobile.system.ui.screen.milling.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.param.MillTransactionParams
import com.gfs.mobile.system.data.remote.NetworkResource
import com.gfs.mobile.system.data.repository.MillTransactionRepository
import com.gfs.mobile.system.extensions.convertAmountToBigDecimal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MillPaymentViewModel @Inject constructor(
    private val repository: MillTransactionRepository
) : ViewModel() {

     private val _uiState = MutableStateFlow(MillPaymentUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTransactionDetail()
    }

    private fun getTransactionDetail() {
        viewModelScope.launch {
            repository.getMillBillingCache().collect { value ->
                _uiState.update { currentState ->
                    currentState.copy(
                        millTransactionParams = value,
                        amountToPay = value?.totalAmount?.toBigDecimal()
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
                amountChange = amountChange,
                balance = BigDecimal(0.0)
            )
        }

        val negativeNumber = amountChange < BigDecimal.ZERO

        if (negativeNumber) {
            _uiState.update { currentState ->
                currentState.copy(
                    balance = amountChange.abs()
                )
            }
        }
    }

    fun saveTransaction() {
        viewModelScope.launch {
            val millTransactionParams = uiState.value.millTransactionParams

            val params = MillTransactionParams(
                chaffWeight = millTransactionParams?.chaffWeight,
                chaffPrice = millTransactionParams?.chaffPrice,
                millPrice = millTransactionParams?.millPrice,
                customerID = millTransactionParams?.customerID,
                deductions = millTransactionParams?.deductions,
                fiftyKgs = millTransactionParams?.fiftyKgs,
                riceWeight = millTransactionParams?.riceWeight,
                sixtyKgs = millTransactionParams?.sixtyKgs,
                subTotal = millTransactionParams?.subTotal,
                thirtyKgs = millTransactionParams?.thirtyKgs,
                totalAmount = millTransactionParams?.totalAmount,
                twentyFiveKgs = millTransactionParams?.twentyFiveKgs,
                entryBy = millTransactionParams?.entryBy,
                amountPaid = uiState.value.amountPaid.toDouble(),
                balance = uiState.value.balance?.toDouble()
            )

            repository.saveMillTransaction(params = params).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        showLoadingDialog = false,
                                        transactionSave = true
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
                                showLoadingDialog = true,
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
    
    fun dismissSuccessDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                transactionSave = false
            )
        }
    }

    fun dismissErrorDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = ""
            )
        }
    }
}