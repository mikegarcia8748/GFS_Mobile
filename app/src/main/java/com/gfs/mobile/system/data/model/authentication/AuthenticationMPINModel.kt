package com.gfs.mobile.system.data.model.authentication


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AuthenticationMPINModel(
    @SerialName("accessToken")
    val accessToken: String?,
    @SerialName("accountType")
    val accountType: String?,
    @SerialName("fullName")
    val fullName: String?,
    @SerialName("userID")
    val userID: String?,
    @SerialName("userName")
    val userName: String?
)