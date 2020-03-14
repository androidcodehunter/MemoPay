package com.memo.pay.data.source.remote

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountDataSource
import kotlinx.coroutines.delay
import com.memo.pay.data.Result.Error
import com.memo.pay.data.Result.Success
import com.memo.pay.data.db.AppDatabase
import com.memo.pay.utils.Constants.ACCOUNT_TYPE_RECEIVED
import com.memo.pay.utils.Constants.ACCOUNT_TYPE_SENT
import com.memo.pay.utils.Constants.CURRENT_ACCOUNT_NUMBER
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import kotlin.collections.LinkedHashMap

/*TODO fake api calling delay time in millis. */
private const val SERVICE_LATENCY_IN_MILLIS = 15000L
/*TODO AccountRemoteDataSource which acts as a backend api to simulate all kinds of account, transaction related data response. */
class AccountRemoteDataSource(private val appDatabase: AppDatabase,
                              private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): AccountDataSource {

    private var TRANSACTIONS_SERVICE_DATA = LinkedHashMap<Int, Transaction>(2)
    private var ACCOUNT_SERVICE_DATA = LinkedHashMap<String, Account>(2)

    init {
        /* TODO initial fake api data loading. This will be removed and each function replaced by retrofit suspend keyword for calling remote api in production. */
        addAccount(Account("1111111111", "Sharif", 1500.00, "AED"))
        addAccount(Account("1111111112", "Imad", 1200.00, "AED"))
        var transaction = Transaction( "Sharifur", ACCOUNT_TYPE_RECEIVED, 10.00, "AED", "", Date(), CURRENT_ACCOUNT_NUMBER, "1111111111")
        transaction.id = 1
        addTransaction(transaction)
        transaction = Transaction( "Sharifur", ACCOUNT_TYPE_RECEIVED, 10.00, "AED", "", Date(), CURRENT_ACCOUNT_NUMBER, "1111111111")
        transaction.id = 2
        addTransaction(transaction)
        transaction = Transaction( "Sharifur", ACCOUNT_TYPE_SENT, 10.00, "AED", "", Date(), CURRENT_ACCOUNT_NUMBER, "1111111111")
        transaction.id = 3
        addTransaction(transaction)
        transaction = Transaction( "Sharifur", ACCOUNT_TYPE_RECEIVED, 10.00, "AED", "", getDaysAgo(1), CURRENT_ACCOUNT_NUMBER, "1111111111")
        transaction.id = 4
        addTransaction(transaction)
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar.time
    }


    private fun addTransaction(transaction: Transaction) {
        Timber.d("transaction id ${transaction.id} transactions ${transaction}")
        TRANSACTIONS_SERVICE_DATA[transaction.id] = transaction
    }

    private fun addAccount(account: Account) {
        ACCOUNT_SERVICE_DATA[account.accountNumber] = account
    }

    /*TODO simulates getTransactionsHistory from remote api*/
    override suspend fun getTransactionsHistory(accountNumber: String): Result<List<Transaction>> {
        // Simulate network by delaying the execution.
        val allTransactions = TRANSACTIONS_SERVICE_DATA.values.toList()
        val transactionsByAccount = allTransactions.filter{ it.senderAccountNumber == accountNumber }.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        if (transactionsByAccount.isNullOrEmpty()){
            return Error(Exception("No Transactions Available"))
        }
        return Success(transactionsByAccount)
    }
    /*TODO simulates getAccount from remote api*/
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

    /*TODO fake send money through api*/
    override suspend fun sendMoney(transaction: Transaction): Result<Transaction> = withContext(ioDispatcher){
        delay(SERVICE_LATENCY_IN_MILLIS)
        //update the local account
        val dbTransaction = appDatabase.transactionDao().getTransaction(transaction.id)
        if (dbTransaction != null){
            return@withContext Error(Exception("You cannot transfer money"))
        }else{
            try {
                val account = appDatabase.accountDao().getAccount(transaction.senderAccountNumber)
                appDatabase.accountDao().updateBalance((account.balance - transaction.transactionAmount), transaction.senderAccountNumber)
                TRANSACTIONS_SERVICE_DATA[transaction.id] = transaction
                return@withContext Success(transaction)
            }catch (e: java.lang.Exception){
                return@withContext Error(Exception("Transfer money is not possible, try again"))
            }
        }
    }

    override suspend fun saveTransactions(transactions: List<Transaction>) {

    }

    /*TODO fake add money here the user’s balance automatically incremented by provided AED. This will be replaced by real api call by suspend function*/
    override suspend fun addMoney(amount: Double, accountNumber: String): Result<Account> = withContext(ioDispatcher){
        delay(SERVICE_LATENCY_IN_MILLIS)
        val account = appDatabase.accountDao().getAccount(accountNumber)
        ACCOUNT_SERVICE_DATA[accountNumber]?.apply {
            balance = account.balance + amount
            return@withContext Success(this)
        }
        return@withContext Error(Exception("Add money is not possible"))
    }

    override suspend fun getFrequentContacts(): Result<List<Account>> = withContext(ioDispatcher){
        delay(SERVICE_LATENCY_IN_MILLIS)
        return@withContext Success(mutableListOf(Account("1111111113", "Sarah Aliaherma", 1500.00, "AED", isOnline = true),
            Account("1111111114", "Talal Shamoun", 1500.00, "AED"),
            Account("1111111115", "Coman Quraishi", 1500.00, "AED", isOnline = true)))
    }

    override suspend fun getContacts(): Result<List<Account>> = withContext(ioDispatcher){
        delay(SERVICE_LATENCY_IN_MILLIS)
        return@withContext Success(mutableListOf(Account("1111111115", "Coman Quraishi", 1500.00, "AED", isFavorite = true, isOnline = true),
            Account("1111111116", "Moe Khalifa", 1500.00, "AED", isFavorite = true),
            Account("1111111113", "Sarah Aliaherma", 1500.00, "AED", isFavorite = true, isOnline = true),
            Account("1111111117", "Abbad Maalouf", 1500.00, "AED"),
            Account("1111111118", "Abbad Maalouf", 1500.00, "AED"),
            Account("1111111119", "Boualem Atiyeh", 1500.00, "AED"),
            Account("1111111120", "Fazl Naifeh", 1500.00, "AED"),
            Account("1111111121", "Jamal Rahal", 1500.00, "AED"),
            Account("1111111122", "Rashad Samaha", 1500.00, "AED"),
            Account("1111111123", "Alim Essa", 1500.00, "AED"),
            Account("1111111124", "Imad Amari", 1500.00, "AED"),
            Account("1111111125", "Nahyan Harb", 1500.00, "AED"),
            Account("1111111126", "Talal Shamoun", 1500.00, "AED"),
            Account("1111111127", "Zafer Morcos", 1500.00, "AED")))
    }




}