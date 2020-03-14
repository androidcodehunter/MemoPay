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

    /*TODO test case to check save account and retrieve account*/
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

}