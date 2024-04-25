package com.gfs.mobile.system.data.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Keep
@Serializable
data class MillTransactionModel(
    @SerialName("chaffWeight")
    val chaffWeight: Double?,
    @SerialName("customerID")
    val customerID: String?,
    @SerialName("dateTimeStamp")
    val dateTimeStamp: String?,
    @SerialName("deductions")
    val deductions: Double?,
    @SerialName("fiftyKilos")
    val fiftyKilos: Int?,
    @SerialName("riceWeight")
    val riceWeight: Double?,
    @SerialName("sixtyKilos")
    val sixtyKilos: Int?,
    @SerialName("subTotal")
    val subTotal: String?,
    @SerialName("thirtyKilos")
    val thirtyKilos: Int?,
    @SerialName("total")
    val total: String?,
    @SerialName("transactionDate")
    val transactionDate: String?,
    @SerialName("twentyFiveKilos")
    val twentyFiveKilos: Int?,
    @SerialName("userID")
    val userID: String?
)