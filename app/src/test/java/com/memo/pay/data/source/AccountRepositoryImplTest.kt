package com.memo.pay.data.source

import com.memo.pay.MainCoroutineRule
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Rule

/*TODO check fake repository impl test to verify error or success case. */
class AccountRepositoryImplTest {

    private val accountSharif = Account(ACCOUNT_NUMBER_SHARIF, "Sharifur Rahaman", 101.00, "BDT")
    private val accountImad = Account(ACCOUNT_NUMBER_IMAD, "Imad", 1001.00, "AED")

    private val transaction1 = Transaction("Sharifur", ACCOUNT_TYPE_RECEIVED, 10.0, CURRENCY_AED, "", Date(), CURRENT_ACCOUNT_NUMBER, ACCOUNT_NUMBER_IMAD)
    private val transaction2 = Transaction("Imad", ACCOUNT_TYPE_SENT, 10.0, CURRENCY_AED, "", Date(), CURRENT_ACCOUNT_NUMBER, ACCOUNT_NUMBER_SHARIF)
    private val transaction3 = Transaction("Sharifur", ACCOUNT_TYPE_SENT, 10.0, CURRENCY_AED, "", Date(), CURRENT_ACCOUNT_NUMBER, ACCOUNT_NUMBER_IMAD)
    private val ransactions = listOf(transaction1, transaction2, transaction3)

    private lateinit var accountRepository: AccountRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository(){
        accountRepository = FakeAccountRepository()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun saveAndRetrieveAccountInfoTest() = mainCoroutineRule.runBlockingTest{
        accountRepository.saveAccount(accountSharif)
        val accountResponse = accountRepository.getAccount(true, accountSharif.accountNumber) as Result.Success
        Assert.assertThat(accountResponse.data, IsEqual(accountSharif))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkUnknownAccountWithError() = mainCoroutineRule.runBlockingTest{
        val result = accountRepository.getAccount(true, "no account")
        // Result should be an error
        ///assertthat(result).isInstanceOf(Result.Error::class.java)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getTransactions_requestAllTransactionFromSavedState() = mainCoroutineRule.runBlockingTest{
        accountRepository.saveTransactions(ransactions)
        val transactions = accountRepository.getTransactionsHistory(true, CURRENT_ACCOUNT_NUMBER) as Result.Success
        Assert.assertThat(transactions.data, IsEqual(ransactions))
    }

    /*TODO check transaction error using unknown account through fake repository*/
    @ExperimentalCoroutinesApi
    @Test
    fun getTransactionsWithError() = mainCoroutineRule.runBlockingTest{
        val transactions = accountRepository.getTransactionsHistory(true, "Unknown Account Number")
        //Check error cases
        ///Assert.assertThat(transactions.data, IsEqual(ransactions))
    }

    /*TODO add money scenario test through fake repository with success message using existing account*/
    @ExperimentalCoroutinesApi
    @Test
    fun addMoneyCurrentBalanceIncreaseTest() = mainCoroutineRule.runBlockingTest{

    }

    /*TODO add money scenario test through fake repository with error message using wrong account*/
    @ExperimentalCoroutinesApi
    @Test
    fun addMoneyCurrentBalanceIncreaseErrorTestWithUnknownAccount() = mainCoroutineRule.runBlockingTest{

    }


    /*TODO send money scenario test through fake repository*/
    @Test
    fun sendMoneyCurrentBalanceDecreaseTest() = mainCoroutineRule.runBlockingTest{

    }

    /*TODO send money scenario test through fake repository with error message using unknown account*/
    @Test
    fun sendMoneyCurrentBalanceDecreaseErrorTestWithUnknownAccount() = mainCoroutineRule.runBlockingTest{

    }

}