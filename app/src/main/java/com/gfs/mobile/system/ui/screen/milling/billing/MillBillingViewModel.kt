package com.gfs.mobile.system.ui.screen.milling.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gfs.mobile.system.data.local.preferences.millbilling.MillBillingCache
import com.gfs.mobile.system.data.local.room.PreferenceResource
import com.gfs.mobile.system.data.model.MillTransactionModel
import com.gfs.mobile.system.data.model.customer.CustomerModel
import com.gfs.mobile.system.data.param.AddCustomerParams
import com.gfs.mobile.system.data.param.MillTransactionParams
import com.gfs.mobile.system.data.remote.NetworkResource
import com.gfs.mobile.system.data.repository.AuthenticationRepository
import com.gfs.mobile.system.data.repository.ChaffPriceRepository
import com.gfs.mobile.system.data.repository.CustomerRepository
import com.gfs.mobile.system.data.repository.MillPriceRepository
import com.gfs.mobile.system.data.repository.MillTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MillBillingViewModel @Inject constructor(
    private val millTransactionRepository: MillTransactionRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val chaffPriceRepository: ChaffPriceRepository,
    private val customerRepository: CustomerRepository,
    private val millPriceRepository: MillPriceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MillBillingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initAuthorizeUser()
        initializeMillingPrice()
        initializeChaffPrice()
        clearPreviousTransactions()
    }

    private fun initAuthorizeUser() {
        viewModelScope.launch {
            authenticationRepository.getAuthenticationToken().collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(
                        authorizeUser = result
                    )
                }
            }
        }
    }

    private fun initializeMillingPrice() {

        viewModelScope.launch {
            millPriceRepository.getMillPrice().collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when(response.data?.status) {
                            "success" -> {
                                val data = response.data.data?.get(0)
                                Timber.d("Milling Price: %s", data?.price)
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        ricePricePerKilo = data
                                    )
                                }
                            }

                            else -> {
                                val errorMessage = response.data?.message
                                Timber.e("Failed fetching mill price. Error message: %s", errorMessage);
                            }
                        }
                    }

                    is NetworkResource.Loading -> {

                    }

                    else -> {
                        val errorMessage = response.error?.message
                        Timber.e("Failed fetching mill price. Error message: %s", errorMessage);
                    }
                }
            }
        }
    }

    private fun initializeChaffPrice() {
        viewModelScope.launch {
            chaffPriceRepository.getChaffPrice().collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when(response.data?.status) {
                            "success" -> {
                                val data = response.data.data?.get(0)
                                Timber.d("Milling Price: %s", data?.buyingPrice)
                                Timber.d("Milling Price: %s", data?.sellingPrice)
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        chaffPricePerKilo = data
                                    )
                                }
                            }

                            else -> {
                                val errorMessage = response.data?.message
                                Timber.e("Failed fetching chaff price. Error message: %s", errorMessage);
                            }
                        }
                    }

                    is NetworkResource.Loading -> {

                    }

                    else -> {
                        val errorMessage = response.error?.message
                        Timber.e("Failed fetching chaff price. Error message: %s", errorMessage);
                    }
                }
            }
        }
    }

    private fun clearPreviousTransactions() {
        viewModelScope.launch {
            millTransactionRepository.clearMillBillingCache()
        }
    }

    fun onSetCustomer(value: CustomerModel) {
        _uiState.update { currentState ->
            currentState.copy(
                customer = value,
                selectCustomer = false,
                searchValue = "",
                customerList = emptyList()
            )
        }
    }

    fun onSearchCustomerName(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                isSearching = true,
                searchValue = value
            )
        }

        if (value.isNotEmpty()) {
            searchCustomer(value)
        }
    }

    fun onClickSelectCustomer() {
        _uiState.update { currentState ->
            currentState.copy(
                selectCustomer = true
            )
        }

        getCustomers()
    }

    fun onCancelSelectCustomer() {
        _uiState.update { currentState ->
            currentState.copy(
                selectCustomer = false,
                searchValue = ""
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
                val millingPrice = _uiState.value.ricePricePerKilo?.price ?: 0.0

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
                val millingPrice = _uiState.value.ricePricePerKilo?.price ?: 0.0

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
                val millingPrice = _uiState.value.ricePricePerKilo?.price ?: 0.0

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
                val millingPrice = _uiState.value.ricePricePerKilo?.price ?: 0.0

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
                val millingPrice = _uiState.value.ricePricePerKilo?.price ?: 0.0

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
                val chaffPrice = _uiState.value.chaffPricePerKilo?.buyingPrice?: 0.0

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
        val millTransaction = MillTransactionParams(
            chaffWeight = uiState.value.chaffWeight?.toDouble(),
            chaffPrice = uiState.value.chaffPricePerKilo?.id,
            millPrice = uiState.value.ricePricePerKilo?.id,
            customerID = uiState.value.customer?.id,
            deductions = 0.0,
            fiftyKgs = uiState.value.rice50Kilos?.toInt(),
            riceWeight = uiState.value.riceCustomWeight?.toDouble(),
            sixtyKgs = uiState.value.rice60Kilos?.toInt(),
            subTotal = uiState.value.billSubTotalAmount.toString(),
            thirtyKgs = uiState.value.rice30Kilos?.toInt(),
            totalAmount = uiState.value.billTotalAmount.toString(),
            twentyFiveKgs = uiState.value.rice25Kilos?.toInt(),
            entryBy = uiState.value.authorizeUser?.userID,
        )

        viewModelScope.launch {
            millTransactionRepository.saveMillBillingToCache(millTransaction)
        }
    }

    private fun getCustomers() {
        viewModelScope.launch {
            delay(500)
            customerRepository.getCustomers(10).collect{ response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {

                                val data = response.data.data

                                _uiState.update { currentState ->
                                    currentState.copy(
                                        isSearching = false,
                                        customerList = data.orEmpty()
                                    )
                                }
                            }

                            else -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        isSearching = false
                                    )
                                }
                            }
                        }
                    }

                    is NetworkResource.Loading -> {

                    }

                    else -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isSearching = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun searchCustomer(customer: String) {
        viewModelScope.launch {
            delay(500)
            customerRepository.searchCustomer(customer).collect{ response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {

                                val data = response.data.data

                                _uiState.update { currentState ->
                                    currentState.copy(
                                        isSearching = false,
                                        customerList = data.orEmpty()
                                    )
                                }
                            }

                            else -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        isSearching = false,
                                        customerList = emptyList()
                                    )
                                }
                            }
                        }
                    }

                    is NetworkResource.Loading -> {

                    }

                    else -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isSearching = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun addCustomer(
        name: String,
        alias: String
    ) {
        viewModelScope.launch {
            val customer = AddCustomerParams(
                name = name,
                alias = alias
            )
            customerRepository.addCustomer(customer).collect { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        when (response.data?.status) {
                            "success" -> {

                                val data = response.data.data

                                _uiState.update { currentState ->
                                    currentState.copy(
                                        showLoadingDialog = false,
                                        customer = data,
                                        selectCustomer = false
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
    }
}