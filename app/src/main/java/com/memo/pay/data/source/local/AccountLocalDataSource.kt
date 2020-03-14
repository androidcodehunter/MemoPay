package com.memo.pay.data.source.local

import com.memo.pay.data.Result
import com.memo.pay.data.db.AppDatabase
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.memo.pay.data.Result.Error
import com.memo.pay.data.Result.Success

/*TODO check all types of database related tasks here which is used to provide local datasource to upstream repository to viewmodel*/
class AccountLocalDataSource(private val appDatabase: AppDatabase,
                             private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): AccountDataSource {

    override suspend fun getTransactionsHistory(accountNumber: String): Result<List<Transaction>> = withContext(ioDispatcher){
        return@withContext try {
            Success(appDatabase.transactionDao().getTransactions(accountNumber))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getAccount(accountNumber: String): Result<Account> = withContext(ioDispatcher){
        try {
            val account = appDatabase.accountDao().getAccount(accountNumber)
            return@withContext Success(account)
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveAccount(account: Account) {
        appDatabase.accountDao().saveAccount(account)
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        appDatabase.transactionDao().saveTransaction(transaction)
    }

    override suspend fun saveTransactions(transactions: List<Transaction>) {
        appDatabase.transactionDao().saveTransactions(transactions)
    }

    override suspend fun addMoney(amount: Double, accountNumber: String): Result<Account> = withContext(ioDispatcher){
        appDatabase.accountDao().updateBalance(amount, accountNumber)
        try {
            val account = appDatabase.accountDao().getAccount(accountNumber)
            return@withContext Success(account)
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun getFrequentContacts(): Result<List<Account>> {
        return Error(Exception())
    }

    override suspend fun getContacts(): Result<List<Account>> {
        return Error(Exception())
    }

    override suspend fun sendMoney(transaction: Transaction): Result<Transaction> = withContext(ioDispatcher){
        appDatabase.transactionDao().saveTransaction(transaction)
        try {
            return@withContext Success(transaction)
        }catch (e: Exception){
            return@withContext Error(e)
        }
    }


}