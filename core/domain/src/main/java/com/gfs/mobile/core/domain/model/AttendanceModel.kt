package com.gfs.mobile.core.domain.model


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
