package com.gfs.mobile.core.data.data.local.room.customer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Customer_Info")
class CustomerEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
