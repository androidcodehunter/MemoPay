package com.memo.pay.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.memo.pay.data.Result
import com.memo.pay.data.db.AppDatabase
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.succeeded
import com.memo.pay.utils.Constants
import com.memo.pay.utils.Constants.ACCOUNT_NUMBER_SHARIF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

val account = Account(ACCOUNT_NUMBER_SHARIF, "Sharifur Test", 1000.00, Constants.CURRENCY_AED)

@RunWith(AndroidJUnit4::class)
@MediumTest
class AccountLocalDataSourceTest {
    private lateinit var database: AppDatabase
    private lateinit var localDataSource: AccountLocalDataSource

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                AppDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()

        localDataSource =
            AccountLocalDataSource(database, Dispatchers.Main)
    }

    @After
    fun cleanUp(){
        database.close()
    }

    @Test
    fun saveAccount_retrievesAccount() = runBlocking{
        //Save account information
        localDataSource.saveAccount(account)
        //get saved account information
        val accountResult = localDataSource.getAccount(ACCOUNT_NUMBER_SHARIF)

        assertThat(accountResult.succeeded, `is`(true))
        accountResult as Result.Success
        assertThat(accountResult.data.name, `is`(account.name))
        assertThat(accountResult.data.balance, `is`(account.balance))
        assertThat(accountResult.data.currency, `is`(account.currency))
    }

    @Test
    fun saveTransactions_retrieveTransactions() = runBlocking{

    }



    /*  override suspend fun getTransactionsHistory(accountNumber: String): Result<List<Transaction>> = withContext(ioDispatcher){
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
*/
}