package com.gfs.mobile.core.domain.model.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AddCustomerParams(
    @SerialName("alias")
    val alias: String?,
    @SerialName("name")
    val name: String?
)
