package com.gfs.mobile.system.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class GetEmployeesModel(
    @SerialName("fullName")
    val fullName: String?,
    @SerialName("_id")
    val id: String?,
    @SerialName("userName")
    val userName: String?
)