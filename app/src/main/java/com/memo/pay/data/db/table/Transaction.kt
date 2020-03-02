package com.memo.pay.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "transactions")
data class Transaction(@PrimaryKey val id: String,
                       val name: String,
                       val type: String,
                       val transactionAmount: Double,
                       val currency: String,
                       val profileUrl: String,
                       val date: Date,
                       val senderAccountNumber: String,
                       val receiverAccountNumber: String)