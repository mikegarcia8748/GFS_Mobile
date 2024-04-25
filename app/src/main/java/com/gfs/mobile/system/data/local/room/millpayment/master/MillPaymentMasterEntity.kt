package com.gfs.mobile.system.data.local.room.millpayment.master

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Mill_Payment_Master")
data class MillPaymentMasterEntity(
    @PrimaryKey @ColumnInfo(name = "TransactionNo") val transactionNo: String? = null,
    @ColumnInfo(name = "UserID") val userIDxx: String? = null,
    @ColumnInfo(name = "Transaction_Type") val transactionType: String? = null,
    @ColumnInfo(name = "Transaction_Date") val transactionDate: String? = null,
    @ColumnInfo(name = "ClientID") val clientID: String? = null,
    @ColumnInfo(name = "Total_Amount") val totalAmount: String? = null,
    @ColumnInfo(name = "Amount_Paid") val amountPaid: String? = null,
    @ColumnInfo(name = "Remaining_Balance") val remainingBalance: String? = null
)