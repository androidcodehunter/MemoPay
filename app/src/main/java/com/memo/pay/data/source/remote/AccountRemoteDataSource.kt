package com.memo.pay.data.source.remote

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountDataSource
import kotlinx.coroutines.delay
import com.memo.pay.data.Result.Error
import com.memo.pay.data.Result.Success

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

class AccountRemoteDataSource: AccountDataSource {


    private var TRANSACTIONS_SERVICE_DATA = LinkedHashMap<String, Transaction>(2)
    private var ACCOUNT_SERVICE_DATA = LinkedHashMap<String, Account>(2)

    init {
        addAccount(Account("", "", 767.00, "AED"))
    }

    private fun addAccount(account: Account) {
        ACCOUNT_SERVICE_DATA[account.accountNumber] = account
    }

    override suspend fun getTransactionsHistory(): Result<List<Transaction>> {
        // Simulate network by delaying the execution.
        val tasks = TRANSACTIONS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Success(tasks)
    }

    override suspend fun getAccount(accountNumber: String): Result<Account> {
        // Simulate network by delaying the execution.
        delay(SERVICE_LATENCY_IN_MILLIS)
        ACCOUNT_SERVICE_DATA[accountNumber]?.let {
            return Success(it)
        }
        return Error(Exception("Task not found"))
    }

    override suspend fun saveAccount(account: Account) {
        ACCOUNT_SERVICE_DATA[account.accountNumber] = account
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        TRANSACTIONS_SERVICE_DATA[transaction.id] = transaction
    }

    override suspend fun saveTransactions(transactions: List<Transaction>) {

    }

}