package com.gfs.mobile.system.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AttendanceTodayModel(
    @SerialName("fullName")
    val fullName: String?,
    @SerialName("isPresent")
    val isPresent: Boolean?,
    @SerialName("userName")
    val userName: String?
)