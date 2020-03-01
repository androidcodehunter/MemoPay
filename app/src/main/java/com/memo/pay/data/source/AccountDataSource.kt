package com.memo.pay.data.source

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction

interface AccountDataSource {
    suspend fun getTransactionsHistory(): Result<List<Transaction>>
    suspend fun getAccount(accountNumber: String): Result<Account>
    suspend fun saveAccount(account: Account)
    suspend fun saveTransaction(transaction: Transaction)
    suspend fun saveTransactions(transactions: List<Transaction>)
}