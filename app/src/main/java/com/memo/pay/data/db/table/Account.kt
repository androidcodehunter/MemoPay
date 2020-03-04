package com.memo.pay.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(@PrimaryKey val accountNumber: String,
                   var name: String,
                   var balance: Double,
                   var currency: String){
    fun getBalanceWithCurrency() = "$currency $balance"
}