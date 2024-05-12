package com.gfs.mobile.system.data.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AddChaffPriceParams(
    @SerialName("buyingPrice")
    val buyingPrice: Double?,
    @SerialName("entryBy")
    val entryBy: String?,
    @SerialName("sellingPrice")
    val sellingPrice: Double?
)