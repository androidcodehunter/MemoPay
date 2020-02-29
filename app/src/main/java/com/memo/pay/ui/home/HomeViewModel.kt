package com.memo.pay.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.memo.pay.data.source.AccountRepository

class HomeViewModel(private val accountRepository: AccountRepository) {



    @Suppress("UNCHECKED_CAST")
    class ResumeViewModelFactory(private val accountRepository: AccountRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            (HomeViewModel(accountRepository) as T)
    }

}