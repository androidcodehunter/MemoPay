package com.memo.pay.ui.home

import com.memo.pay.data.source.FakeTestAccountRepository
import com.memo.pay.ui.viewmodel.HomeViewModel
import org.junit.Before

class HomeViewModelTest{

    private lateinit var accountRepository: FakeTestAccountRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setupViewModel(){
        accountRepository = FakeTestAccountRepository()
       // val transaction1 = Transaction("1", "Sharifur", "sent", 10.00, "AED", "", Date(), "111111", "111112")
       // val transaction2 = Transaction("2", "Imad", "sent", 10.00, "AED", "", Date(), "111111", "111112")
       // val transaction3 = Transaction("3", "Sharifur", "sent", 10.00, "AED", "", Date(), "111111", "111112")
      //  accountRepository.addTransactions(transaction1, transaction2, transaction3)
        homeViewModel =
            HomeViewModel(accountRepository)
    }

}