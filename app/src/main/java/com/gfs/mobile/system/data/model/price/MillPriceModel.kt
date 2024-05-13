package com.gfs.mobile.system.data.model.price


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class MillPriceModel(
    @SerialName("entryBy")
    val entryBy: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("price")
    val price: Double?
)