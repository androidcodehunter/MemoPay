package com.memo.pay.data.source

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction

interface AccountRepository {
    suspend fun getTransactionsHistory(forceUpdate: Boolean = false, accountNumber: String): Result<List<Transaction>>
    suspend fun getAccount(forceUpdate: Boolean = false, accountNumber: String): Result<Account>
}