package com.memo.pay.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
/*TODO room data table for holding transaction information locally*/
@Entity(tableName = "transactions")
data class Transaction(val name: String,
                       val type: String,
                       val transactionAmount: Double,
                       val currency: String,
                       val profileUrl: String,
                       val date: Date,
                       val senderAccountNumber: String,
                       val receiverAccountNumber: String){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}