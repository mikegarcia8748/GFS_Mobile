package com.gfs.mobile.system.data.param


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class CreateAttendanceParams(
    @SerialName("entryBy")
    val entryBy: String?,
    @SerialName("workerID")
    val workerID: String?
)