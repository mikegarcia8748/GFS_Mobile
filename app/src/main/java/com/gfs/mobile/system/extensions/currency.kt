package com.gfs.mobile.system.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.toPhp(currency: String? = "₱"): String {
    val mCurrency = if (!currency.isNullOrEmpty()) currency else "₱"
    return if (mCurrency.length == 1) {
        "$mCurrency ${this.formatAmount()}"
    } else {
        "${this.formatAmount()} $mCurrency"
    }
}

fun BigDecimal.formatAmount(): String {
    val df = DecimalFormat("#,##0.00")
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    return df.format(this)
}

/**
 * Returns the value of this number (may include comma) as a [BigDecimal],
 * which may involve rounding.
 */
fun String.convertAmountToBigDecimal(): BigDecimal {
    val format = NumberFormat.getInstance(Locale.US) as DecimalFormat
    format.isParseBigDecimal = true
    return format.parse(this.ifEmpty { "0" }) as BigDecimal
}

/**
 * Format the [this] as an amount with [currency]
 * (format: amount separator and decimal places).
 *
 * Take note that the symbol currencies will be placed
 * in the front of the amount whereas ISO currencies
 * will be placed at the end of the amount.
 */
fun BigDecimal.formatAmountWithCurrency(currency: String? = "₱"): String {
    val mCurrency = if (!currency.isNullOrEmpty()) currency else "₱"
    return if (mCurrency.length == 1) {
        "$mCurrency ${this.formatAmount()}"
    } else {
        "${this.formatAmount()} $mCurrency"
    }
}

fun String.formatAmountToCurrency(currency: String? = "₱") : String {
    return this.convertAmountToBigDecimal().formatAmountWithCurrency(currency = currency)
}