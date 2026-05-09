package com.gfs.mobile.core.domain.model.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class CreateAttendanceParams(
    @SerialName("entryBy")
    val entryBy: String?,
    @SerialName("workerID")
    val workerID: String?,
    @SerialName("businessLineID")
    val businessLineID: String?,
    @SerialName("status")
    val status: String?
)
