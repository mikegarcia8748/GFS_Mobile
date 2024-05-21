package com.gfs.mobile.system.data.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AuthenticateMPINParams(
    @SerialName("mpin")
    val mpin: String?,
    @SerialName("userName")
    val userName: String?
)