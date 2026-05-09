package com.gfs.mobile.core.data.data.local.room.millpayment.detail

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Mill_Payment_Detail", primaryKeys = ["", ""])
data class MillPaymentDetailEntity(
    @ColumnInfo(name = "TransactionNo") val transactionNo: String? = null,
//    @ColumnInfo(name = " ")
)
