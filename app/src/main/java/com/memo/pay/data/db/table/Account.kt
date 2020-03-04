package com.memo.pay.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(@PrimaryKey val accountNumber: String,
                   val name: String,
                   val balance: Double,
                   val currency: String){
    fun getBalanceWithCurrency() = "$currency $balance"
}