package com.gfs.mobile.system.data.model.customer


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class CustomerModel(
    @SerialName("alias")
    val alias: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
)