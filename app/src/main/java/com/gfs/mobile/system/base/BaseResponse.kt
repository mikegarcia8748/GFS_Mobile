package com.gfs.mobile.system.base


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class BaseResponse<T>(
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("statusCode")
    val statusCode: Int? = null
)