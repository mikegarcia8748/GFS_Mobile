package com.gfs.mobile.system.data.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class MillTransactionParams(
    @SerialName("amountPaid")
    val amountPaid: Double? = null,
    @SerialName("balance")
    val balance: Double? = null,
    @SerialName("chaffPrice")
    val chaffPrice: String? = null,
    @SerialName("chaffWeight")
    val chaffWeight: Double? = null,
    @SerialName("customerID")
    val customerID: String? = null,
    @SerialName("deductions")
    val deductions: Double? = null,
    @SerialName("entryBy")
    val entryBy: String? = null,
    @SerialName("fiftyKgs")
    val fiftyKgs: Int? = null,
    @SerialName("millPrice")
    val millPrice: String? = null,
    @SerialName("riceWeight")
    val riceWeight: Double? = null,
    @SerialName("sixtyKgs")
    val sixtyKgs: Int? = null,
    @SerialName("subTotal")
    val subTotal: String? = null,
    @SerialName("thirtyKgs")
    val thirtyKgs: Int? = null,
    @SerialName("totalAmount")
    val totalAmount: String? = null,
    @SerialName("twentyFiveKgs")
    val twentyFiveKgs: Int?
)