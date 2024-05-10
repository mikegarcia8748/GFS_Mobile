package com.gfs.mobile.system.data.model.authorizeusers


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AuthorizeUsers(
    @SerialName("accountType")
    val accountType: String?,
    @SerialName("userName")
    val userName: String?
)