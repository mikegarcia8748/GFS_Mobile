package com.gfs.mobile.system.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class AttendanceModel(
    @SerialName("date")
    val date: String?,
    @SerialName("entryBy")
    val entryBy: String?
)