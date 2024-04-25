package com.gfs.mobile.system.data.model.payroll


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class DailySalaryDetail(
    @SerialName("day")
    val day: String?,
    @SerialName("dividends")
    val dividends: String?,
    @SerialName("dividedAmount")
    val dividedAmount: String?,
    @SerialName("expenses")
    val expenses: List<Expense?>?,
    @SerialName("isPresent")
    val isPresent: Boolean?,
    @SerialName("grossEarnings")
    val grossEarnings: String?,
    @SerialName("netSalary")
    val netSalary: String?
)