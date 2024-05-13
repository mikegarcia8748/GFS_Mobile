package com.gfs.mobile.system.data.local.preferences.millbilling

import com.gfs.mobile.system.data.param.MillTransactionParams
import kotlinx.coroutines.flow.Flow

interface MillBillingCache {

    suspend fun saveMillBilling(value: MillTransactionParams)

    fun getMillBilling() : Flow<MillTransactionParams?>

    suspend fun clear()
}