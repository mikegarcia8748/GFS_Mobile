package com.gfs.mobile.core.data.data.local.preferences.millbilling

import com.gfs.mobile.core.domain.model.param.MillTransactionParams
import kotlinx.coroutines.flow.Flow

interface MillBillingCache {

    suspend fun saveMillBilling(value: MillTransactionParams)

    fun getMillBilling() : Flow<MillTransactionParams?>

    suspend fun clear()
}
