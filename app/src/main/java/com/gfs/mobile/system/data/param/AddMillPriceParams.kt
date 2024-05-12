package com.gfs.mobile.system.data.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AddMillPriceParams(
    @SerialName("entryBy")
    val entryBy: String?,
    @SerialName("price")
    val price: Double?
)