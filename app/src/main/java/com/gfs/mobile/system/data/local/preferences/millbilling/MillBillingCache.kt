package com.gfs.mobile.system.data.local.preferences.millbilling

import com.gfs.mobile.system.data.model.MillTransactionModel
import kotlinx.coroutines.flow.Flow

interface MillBillingCache {

    suspend fun saveMillBilling(value: MillTransactionModel)

    fun getMillBilling() : Flow<MillTransactionModel?>

    suspend fun clear()
}