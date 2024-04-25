package com.gfs.mobile.system.data.model.payroll


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class WorkerPayrollDetail(
    @SerialName("alias")
    val alias: String?,
    @SerialName("dailySalaryDetail")
    val dailySalaryDetail: List<DailySalaryDetail?>?,
    @SerialName("deductedLoan")
    val deductedLoan: List<DeductedLoan>?,
    @SerialName("fullname")
    val fullname: String?,
    @SerialName("grossSalary")
    val grossSalary: String?,
    @SerialName("netSalary")
    val netSalary: String?,
    @SerialName("UserID")
    val userID: String?,
    @SerialName("wagePercentage")
    val wagePercentage: String?
)