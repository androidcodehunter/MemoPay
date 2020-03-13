package com.memo.pay.data.source

import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.*
import com.memo.pay.data.Result
import com.memo.pay.utils.Constants.ACCOUNT_NUMBER_IMAD
import com.memo.pay.utils.Constants.ACCOUNT_NUMBER_SHARIF
import com.memo.pay.utils.Constants.ACCOUNT_TYPE_RECEIVED
import com.memo.pay.utils.Constants.ACCOUNT_TYPE_SENT
import com.memo.pay.utils.Constants.CURRENCY_AED
import com.memo.pay.utils.Constants.CURRENT_ACCOUNT_NUMBER
import org.hamcrest.core.IsEqual
import org.junit.Assert

class AccountRepositoryImplTest {

    private val accountSharif = Account("111111", "Sharifur Rahaman", 101.00, "BDT")
    private val accountImad = Account("111112", "Imad", 1001.00, "AED")
    private val accountsLocal = listOf(accountSharif, accountImad)
    private val accountsRemote = listOf(accountSharif, accountImad)

    private val transaction1 = Transaction("Sharifur", ACCOUNT_TYPE_RECEIVED, 10.0, CURRENCY_AED, "", Date(), CURRENT_ACCOUNT_NUMBER, ACCOUNT_NUMBER_IMAD)
    private val transaction2 = Transaction("Imad", ACCOUNT_TYPE_SENT, 10.0, CURRENCY_AED, "", Date(), CURRENT_ACCOUNT_NUMBER, ACCOUNT_NUMBER_SHARIF)
    private val transaction3 = Transaction("Sharifur", ACCOUNT_TYPE_SENT, 10.0, CURRENCY_AED, "", Date(), CURRENT_ACCOUNT_NUMBER, ACCOUNT_NUMBER_IMAD)
    private val remoteTransactions = listOf(transaction1, transaction2, transaction3)

    private lateinit var accountRemoteDataSource: FakeAccountDataSource
    private lateinit var accountLocalDataSource: FakeAccountDataSource

    private lateinit var accountRepository: AccountRepository

    @Before
    fun createRepository(){
        accountRemoteDataSource = FakeAccountDataSource(
            transactions = remoteTransactions.toMutableList(),
            accounts = accountsRemote.toMutableList()
        )
        accountLocalDataSource = FakeAccountDataSource(
            transactions = remoteTransactions.toMutableList(),
            accounts = accountsLocal.toMutableList()
        )
        accountRepository = AccountRepositoryImpl(
            accountLocalDataSource,
            accountRemoteDataSource
        )
    }

    @Test
    fun getTransactions_requestAllTransactionFromRemoteDataSource() = runBlockingTest{
        val transactions = accountRepository.getTransactionsHistory(true, "11111111") as Result.Success
        Assert.assertThat(transactions.data, IsEqual(remoteTransactions))
    }


    @Test
    fun getAccount_requestAccountFromRemoteDataSource() = runBlockingTest {
        val account = accountRepository.getAccount(accountNumber = "111112") as Result.Success
        println(account)
        Assert.assertThat(account.data, IsEqual(accountImad))
    }


}