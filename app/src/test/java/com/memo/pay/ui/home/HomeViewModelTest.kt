package com.memo.pay.ui.home

import com.memo.pay.LiveDataTestUtil
import com.memo.pay.MainCoroutineRule
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.FakeAccountRepository
import com.memo.pay.ui.viewmodel.HomeViewModel
import com.memo.pay.utils.Constants
import com.memo.pay.utils.Constants.ACCOUNT_NUMBER_IMAD
import com.memo.pay.utils.Constants.ACCOUNT_NUMBER_SHARIF
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineContext
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class HomeViewModelTest{
    private val accountSharif = Account(ACCOUNT_NUMBER_SHARIF, "Sharifur Rahaman", 101.00, "BDT")
    private val accountImad = Account(ACCOUNT_NUMBER_IMAD, "Imad", 1001.00, "AED")

    private val transaction1 = Transaction("Sharifur", Constants.ACCOUNT_TYPE_RECEIVED, 10.0, Constants.CURRENCY_AED, "", Date(), Constants.CURRENT_ACCOUNT_NUMBER, Constants.ACCOUNT_NUMBER_IMAD)
    private val transaction2 = Transaction("Imad", Constants.ACCOUNT_TYPE_SENT, 10.0, Constants.CURRENCY_AED, "", Date(), Constants.CURRENT_ACCOUNT_NUMBER, Constants.ACCOUNT_NUMBER_SHARIF)
    private val transaction3 = Transaction("Sharifur", Constants.ACCOUNT_TYPE_SENT, 10.0, Constants.CURRENCY_AED, "", Date(), Constants.CURRENT_ACCOUNT_NUMBER, Constants.ACCOUNT_NUMBER_IMAD)
    private val ransactions = listOf(transaction1, transaction2, transaction3)

    private lateinit var accountRepository: FakeAccountRepository
    private lateinit var homeViewModel: HomeViewModel
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setupViewModel() = mainCoroutineRule.runBlockingTest{

        accountRepository = FakeAccountRepository()
        //Save all initial accounts
        accountRepository.saveAccount(accountSharif)
        accountRepository.saveAccount(accountImad)
        //Save all initial transactions
        accountRepository.saveTransactions(ransactions)

        homeViewModel = HomeViewModel(accountRepository)
    }

    @Test
    fun saveAccount_showAccountInformation() = mainCoroutineRule.runBlockingTest{
        val account = homeViewModel.getAccount(true, ACCOUNT_NUMBER_SHARIF)

        val accountValue = LiveDataTestUtil.getValue(account)

        println(accountValue)
        println(account)
    }

    @Test
    fun addMoneyTest(){
       // homeViewModel.saveAccount()
       /// homeViewModel.addMoney()
    }

}