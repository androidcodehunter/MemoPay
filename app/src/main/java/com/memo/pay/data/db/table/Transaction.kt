package com.memo.pay.data.db.table

import java.util.*

data class Transaction(val name: String,
                       val type: String,
                       val transactionAmount: Double,
                       val currency: String,
                       val profileUrl: String,
                       val date: Date)