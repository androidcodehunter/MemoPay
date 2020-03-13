package com.memo.pay.data.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.memo.pay.data.db.AppDatabase
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.utils.Constants
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class TransactionDaoTest {

    private lateinit var database: AppDatabase

    @Before
    fun initDb(){
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() = database.close()

    /*TODO transaction test to check if the transactions saved accordingly*/
    @Test
    fun saveTransactionAndCheckSavedTransaction() = runBlocking {
        val transactionDao = database.transactionDao()

        var transaction = Transaction( "Sharifur", Constants.ACCOUNT_TYPE_RECEIVED, 10.00, "AED", "", Date(), Constants.CURRENT_ACCOUNT_NUMBER, "1111111111")
        transaction.id = 101

        transactionDao.saveTransaction(transaction)
        //Get the saved account
        val savedTransaction = transactionDao.getTransaction(101)
        //Verify if the transaction is saved accordingly.
        assertThat(savedTransaction, notNullValue())
        assertThat(transaction.transactionAmount, `is`(savedTransaction.transactionAmount))
        assertThat(transaction.type, `is`(savedTransaction.type))
        assertThat(transaction.currency, `is`(savedTransaction.currency))
    }

}