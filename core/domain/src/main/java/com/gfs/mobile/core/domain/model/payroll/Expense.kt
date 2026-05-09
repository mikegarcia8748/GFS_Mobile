package com.gfs.mobile.core.domain.model.payroll


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Expense(
    @SerialName("diesel")
    val label: String?,
    @SerialName("meal")
    val amount: String?
)
