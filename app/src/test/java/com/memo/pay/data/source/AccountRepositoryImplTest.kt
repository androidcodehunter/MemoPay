package com.memo.pay.data.source

import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.*
import com.memo.pay.data.Result
import org.hamcrest.core.IsEqual
import org.junit.Assert

class AccountRepositoryImplTest {

    private val accountSharif = Account("111111", "Sharifur Rahaman", 101.00, "BDT")
    private val accountImad = Account("111112", "Imad", 1001.00, "AED")
    private val accounts = listOf(accountSharif, accountImad)
    private val transaction1 = Transaction("1", "Sharifur", "sent", 10.00, "AED", "", Date(), "111111", "111112")
    private val transaction2 = Transaction("2", "Imad", "sent", 10.00, "AED", "", Date(), "111111", "111112")
    private val transaction3 = Transaction("3", "Sharifur", "sent", 10.00, "AED", "", Date(), "111111", "111112")
    private val remoteTransactions = listOf(transaction1, transaction2, transaction3)

    private lateinit var accountRemoteDataSource: FakeAccountDataSource
    private lateinit var accountLocalDataSource: FakeAccountDataSource

    private lateinit var accountRepositoryImpl: AccountRepositoryImpl

    @Before
    fun createRepository(){
        accountRemoteDataSource = FakeAccountDataSource(transactions = remoteTransactions.toMutableList(), accounts = accounts.toMutableList())
        accountLocalDataSource = FakeAccountDataSource(transactions = remoteTransactions.toMutableList(), accounts = accounts.toMutableList())
        accountRepositoryImpl = AccountRepositoryImpl(accountLocalDataSource, accountRemoteDataSource)
    }

    @Test
    fun getTransactions_requestAllTransactionFromRemoteDataSource() = runBlockingTest{
        val transactions = accountRepositoryImpl.getTransactionsHistory() as Result.Success
        Assert.assertThat(transactions.data, IsEqual(remoteTransactions))
    }

}