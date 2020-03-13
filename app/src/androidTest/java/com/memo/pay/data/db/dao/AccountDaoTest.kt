package com.memo.pay.data.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.memo.pay.data.db.AppDatabase
import com.memo.pay.data.db.table.Account
import com.memo.pay.utils.Constants.ACCOUNT_NUMBER_IMAD
import com.memo.pay.utils.Constants.CURRENCY_AED
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
/*TODO Check all the test related to @AccountDao*/
@RunWith(AndroidJUnit4::class)
@SmallTest
class AccountDaoTest {

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

    @Test
    fun saveAccountAndGetByAccountNumber() = runBlocking {
        val accountDao = database.accountDao()

        val account = Account(ACCOUNT_NUMBER_IMAD, "ImadTest", 1000.00, CURRENCY_AED)

        accountDao.saveTestAccount(account)
        //Get the saved account
        val savedAccount = accountDao.getAccount(ACCOUNT_NUMBER_IMAD)
        //Verify if the account is saved accordingly.
        assertThat(savedAccount, notNullValue())
        assertThat(savedAccount.accountNumber, `is`(account.accountNumber))
        assertThat(savedAccount.balance, `is`(account.balance))
        assertThat(savedAccount.name, `is`(account.name))
    }


    @Test
    fun saveAccountAndUpdateBalanceThenCheckByAccountNumber() = runBlocking{
        val accountDao = database.accountDao()
        val account = Account(ACCOUNT_NUMBER_IMAD, "ImadTest", 1000.00, CURRENCY_AED)
        accountDao.saveTestAccount(account)
        accountDao.updateBalance(1200.0, ACCOUNT_NUMBER_IMAD)

        val savedAccount = accountDao.getAccount(ACCOUNT_NUMBER_IMAD)
        assertThat(savedAccount, notNullValue())
        assertThat(savedAccount.balance, `is`(1200.0))
    }

}