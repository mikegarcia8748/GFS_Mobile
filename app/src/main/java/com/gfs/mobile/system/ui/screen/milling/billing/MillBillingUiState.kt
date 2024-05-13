package com.gfs.mobile.system.ui.screen.milling.billing

import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.system.data.model.customer.CustomerModel
import com.gfs.mobile.system.data.model.price.ChaffPriceModel
import com.gfs.mobile.system.data.model.price.MillPriceModel
import java.math.BigDecimal

data class MillBillingUiState(
    val authorizeUser: AuthenticationMPINModel? = null,
    val customer: CustomerModel? = null,
    val selectCustomer: Boolean = false,

    val isSearching: Boolean = false,
    val customerList: List<CustomerModel> = emptyList(),
    val searchValue: String = "",
    val showLoadingDialog: Boolean = false,
    val errorMessage: String? = null,

    //
    val ricePricePerKilo: MillPriceModel? = null,
    val riceCustomWeight: String? = null,

    // Mill Billing...
    val rice60Kilos: String? = null,
    val rice50Kilos: String? = null,
    val rice30Kilos: String? = null,
    val rice25Kilos: String? = null,
    val riceCustomWeightPrice: BigDecimal = BigDecimal(0.0),
    val rice60KilosPrice: BigDecimal = BigDecimal(0.0),
    val rice50KilosPrice: BigDecimal = BigDecimal(0.0),
    val rice30KilosPrice: BigDecimal = BigDecimal(0.0),
    val rice25KilosPrice: BigDecimal = BigDecimal(0.0),
    val chaffWeight: String? = null,
    val chaffPricePerKilo: ChaffPriceModel? = null,
    val chaffPrice: BigDecimal = BigDecimal(0.0),
    val billSubTotalAmount: BigDecimal = BigDecimal(0.0),
    val billTotalAmount: BigDecimal = BigDecimal(0.0),
)