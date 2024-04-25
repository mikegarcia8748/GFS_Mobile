package com.gfs.mobile.system.ui.screen.milling.billing

import java.math.BigDecimal

data class MillBillingUiState(
    val customerName: String = "",
    val isSearching: Boolean = false,
    val searchValue: String = "",
    val ricePricePerKilo: Double = 0.0,
    val riceCustomWeight: String? = null,
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
    val chaffPricePerKilo: Double = 0.0,
    val chaffPrice: BigDecimal = BigDecimal(0.0),
    val billSubTotalAmount: BigDecimal = BigDecimal(0.0),
    val billTotalAmount: BigDecimal = BigDecimal(0.0),
)