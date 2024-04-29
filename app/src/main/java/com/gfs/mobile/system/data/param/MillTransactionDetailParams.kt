package com.gfs.mobile.system.data.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class MillTransactionDetailParams(
    @SerialName("balance")
    val balance: String?,
    @SerialName("chaffPrice")
    val chaffPrice: String?,
    @SerialName("chaffWeight")
    val chaffWeight: String?,
    @SerialName("customerID")
    val customerID: String?,
    @SerialName("deductions")
    val deductions: String?,
    @SerialName("fiftyKilos")
    val fiftyKilos: String?,
    @SerialName("millPrice")
    val millPrice: String?,
    @SerialName("paidAmount")
    val paidAmount: String?,
    @SerialName("riceWeight")
    val riceWeight: String?,
    @SerialName("sixtyKilos")
    val sixtyKilos: String?,
    @SerialName("subTotal")
    val subTotal: String?,
    @SerialName("thirtyKilos")
    val thirtyKilos: String?,
    @SerialName("total")
    val total: String?,
    @SerialName("transactionDate")
    val transactionDate: String?,
    @SerialName("twentyFiveKilos")
    val twentyFiveKilos: String?,
    @SerialName("userID")
    val userID: String?
)