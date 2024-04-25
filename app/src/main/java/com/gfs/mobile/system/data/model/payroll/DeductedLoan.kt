package com.gfs.mobile.system.data.model.payroll

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class DeductedLoan(
    @SerialName("amount")
    val amount: String?
)