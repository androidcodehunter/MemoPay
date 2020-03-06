package com.memo.pay.data.source.remote

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountDataSource
import kotlinx.coroutines.delay
import com.memo.pay.data.Result.Error
import com.memo.pay.data.Result.Success
import com.memo.pay.data.db.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.LinkedHashMap

/*TODO fake api calling delay time in millis. */
private const val SERVICE_LATENCY_IN_MILLIS = 200L

class AccountRemoteDataSource(private val appDatabase: AppDatabase,
                              private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): AccountDataSource {

    private var TRANSACTIONS_SERVICE_DATA = LinkedHashMap<String, Transaction>(2)
    private var ACCOUNT_SERVICE_DATA = LinkedHashMap<String, Account>(2)

    init {
        /* TODO initial fake api data loading. This will be removed and each function replaced by retrofit suspend keyword for calling remote api in production. */
        addAccount(Account("1111111111", "Sharif", 1500.00, "AED"))
        addAccount(Account("1111111112", "Imad", 1200.00, "AED"))
        addTransaction(Transaction("1", "Sharifur", "sent", 10.00, "AED", "", Date(), "1111111111", "1111111111"))
        addTransaction(Transaction("2", "Sharifur", "sent", 10.00, "AED", "", Date(), "1111111111", "1111111111"))
        addTransaction(Transaction("3", "Sharifur", "sent", 10.00, "AED", "", Date(), "1111111111", "1111111111"))
        addTransaction(Transaction("4", "Sharifur", "received", 10.00, "AED", "", Date(), "1111111112", "1111111111"))
    }

    private fun addTransaction(transaction: Transaction) {
        TRANSACTIONS_SERVICE_DATA[transaction.id] = transaction
    }

    private fun addAccount(account: Account) {
        ACCOUNT_SERVICE_DATA[account.accountNumber] = account
    }

    override suspend fun getTransactionsHistory(accountNumber: String): Result<List<Transaction>> {
        // Simulate network by delaying the execution.
        val allTransactions = TRANSACTIONS_SERVICE_DATA.values.toList()
        val transactionsByAccount = allTransactions.filter{ it.accountNumber == accountNumber }.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Success(transactionsByAccount)
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

    /*TODO fake add money here the user’s balance automatically incremented by provided AED. This will be replaced by real api call*/
    override suspend fun addMoney(amount: Double, accountNumber: String): Result<Account> = withContext(ioDispatcher){
        delay(SERVICE_LATENCY_IN_MILLIS)
        val account = appDatabase.accountDao().getAccount(accountNumber)
        ACCOUNT_SERVICE_DATA[accountNumber]?.apply {
            balance = account.balance + amount
            return@withContext Success(this)
        }
        return@withContext Error(Exception("Add money is not possible"))
    }


}