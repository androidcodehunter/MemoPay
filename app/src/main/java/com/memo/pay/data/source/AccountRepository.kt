package com.memo.pay.data.source

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction

interface AccountRepository {
    suspend fun getTransactionsHistory(forceUpdate: Boolean = false, accountNumber: String): Result<List<Transaction>>
    suspend fun getAccount(forceUpdate: Boolean = false, accountNumber: String): Result<Account>
    suspend fun addMoney(amount: Double, accountNumber: String): Result<Account>
    suspend fun getFrequentContacts(): Result<List<Account>>
    suspend fun getContacts(): Result<List<Account>>
    suspend fun sendMoney(transaction: Transaction): Result<Transaction>
    suspend fun saveAccount(account: Account)
    suspend fun saveTransaction(transaction: Transaction)
    suspend fun saveTransactions(transactions: List<Transaction>)
}