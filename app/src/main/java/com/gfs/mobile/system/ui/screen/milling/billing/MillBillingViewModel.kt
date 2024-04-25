package com.gfs.mobile.system.ui.screen.milling.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.local.preferences.millbilling.MillBillingCache
import com.gfs.mobile.system.data.model.MillTransactionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MillBillingViewModel @Inject constructor(
    private val millBillingCache: MillBillingCache
) : ViewModel() {

    private val _uiState = MutableStateFlow(MillBillingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initializeMillingPrice()
    }

    private fun initializeMillingPrice() {
        _uiState.update { currentState ->
            currentState.copy(
                ricePricePerKilo = 4.5,
                chaffPricePerKilo = 8.0
            )
        }
    }

    fun onSetCustomerName(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                customerName = value
            )
        }
    }

    fun onSearchCustomerName(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchValue = value
            )
        }
    }

    fun onClickSearch() {
        _uiState.update { currentState ->
            currentState.copy(
                isSearching = true
            )
        }
    }

    fun onCancelCustomerSearch() {
        _uiState.update { currentState ->
            currentState.copy(
                isSearching = false,
                searchValue = ""
            )
        }
    }

    fun onSetCustomWeight(value: String) {
        try {
            if (value.trim().isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        riceCustomWeight = value,
                        riceCustomWeightPrice = BigDecimal(0.0)
                    )
                }
            } else {
                val millingPrice = _uiState.value.ricePricePerKilo

                val subTotal: Double = millingPrice.times(value.toDouble())

                _uiState.update { currentState ->
                    currentState.copy(
                        riceCustomWeight = value,
                        riceCustomWeightPrice = subTotal.toBigDecimal()
                    )
                }
            }
            calculateSubTotal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSet60Kilos(value: String) {
        try {
            if (value.trim().isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        rice60Kilos = value,
                        rice60KilosPrice = BigDecimal(0.0)
                    )
                }
            } else {
                val millingPrice = _uiState.value.ricePricePerKilo

                val totalKilos = 60.times(value.toDouble())
                val subTotal: Double = millingPrice.times(totalKilos)

                _uiState.update { currentState ->
                    currentState.copy(
                        rice60Kilos = value,
                        rice60KilosPrice = subTotal.toBigDecimal()
                    )
                }
            }
            calculateSubTotal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSet50Kilos(value: String) {
        try {
            if (value.trim().isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        rice50Kilos = value,
                        rice50KilosPrice = BigDecimal(0.0)
                    )
                }
            } else {
                val millingPrice = _uiState.value.ricePricePerKilo

                val totalKilos = 50.times(value.toDouble())
                val subTotal: Double = millingPrice.times(totalKilos)

                _uiState.update { currentState ->
                    currentState.copy(
                        rice50Kilos = value,
                        rice50KilosPrice = subTotal.toBigDecimal()
                    )
                }
            }
            calculateSubTotal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSet30Kilos(value: String) {
        try {
            if (value.trim().isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        rice30Kilos = value,
                        rice30KilosPrice = BigDecimal(0.0)
                    )
                }
            } else {
                val millingPrice = _uiState.value.ricePricePerKilo

                val totalKilos = 30.times(value.toDouble())
                val subTotal: Double = millingPrice.times(totalKilos)

                _uiState.update { currentState ->
                    currentState.copy(
                        rice30Kilos = value,
                        rice30KilosPrice = subTotal.toBigDecimal()
                    )
                }
            }
            calculateSubTotal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSet25Kilos(value: String) {
        try {
            if (value.trim().isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        rice25Kilos = value,
                        rice25KilosPrice = BigDecimal(0.0)
                    )
                }
            } else {
                val millingPrice = _uiState.value.ricePricePerKilo

                val totalKilos = 25.times(value.toDouble())
                val subTotal: Double = millingPrice.times(totalKilos)

                _uiState.update { currentState ->
                    currentState.copy(
                        rice25Kilos = value,
                        rice25KilosPrice = subTotal.toBigDecimal()
                    )
                }
            }
            calculateSubTotal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSetChaffWeight(value: String) {
        try {
            if (value.trim().isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        chaffWeight = value,
                        chaffPrice = BigDecimal(0.0)
                    )
                }
            } else {
                val chaffPrice = _uiState.value.chaffPricePerKilo

                val subTotal: Double = chaffPrice.times(value.toDouble())

                _uiState.update { currentState ->
                    currentState.copy(
                        chaffWeight = value,
                        chaffPrice = subTotal.toBigDecimal()
                    )
                }
            }
            calculateSubTotal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun calculateSubTotal() {
        try {
            val riceCustomWeightPrice = _uiState.value.riceCustomWeightPrice
            val rice60KilosPrice = _uiState.value.rice60KilosPrice
            val rice50KilosPrice = _uiState.value.rice50KilosPrice
            val rice30KilosPrice = _uiState.value.rice30KilosPrice
            val rice25KilosPrice = _uiState.value.rice25KilosPrice
            val subTotal: BigDecimal = riceCustomWeightPrice + rice60KilosPrice +
                    rice50KilosPrice + rice30KilosPrice + rice25KilosPrice

            val chaffPrice = _uiState.value.chaffPrice

            val billTotal = subTotal.minus(chaffPrice)

            _uiState.update { currentState ->
                currentState.copy(
                    billSubTotalAmount = subTotal,
                    billTotalAmount = billTotal
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun proceedToPay() {
        val millTransaction = MillTransactionModel(
            chaffWeight = uiState.value.chaffWeight?.toDouble(),
            customerID = "",
            dateTimeStamp = "",
            deductions = 0.0,
            fiftyKilos = uiState.value.rice50Kilos?.toInt(),
            riceWeight = uiState.value.riceCustomWeight?.toDouble(),
            sixtyKilos = uiState.value.rice60Kilos?.toInt(),
            subTotal = uiState.value.billSubTotalAmount.toString(),
            thirtyKilos = uiState.value.rice30Kilos?.toInt(),
            total = uiState.value.billTotalAmount.toString(),
            transactionDate = "",
            twentyFiveKilos = uiState.value.rice25Kilos?.toInt(),
            userID = "",
        )

        viewModelScope.launch {
            millBillingCache.saveMillBilling(millTransaction)
        }
    }
}