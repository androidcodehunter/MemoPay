package com.memo.pay.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountRepository
import java.util.*

class HomeViewModel(private val accountRepository: AccountRepository): ViewModel() {

    fun getTransactions(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add("12: 50")
        list.add(Transaction("1", "Sharifur", "sent", 10.00, "AED", "", Date()))
        return list
    }

    @Suppress("UNCHECKED_CAST")
    class HomeViewModelFactory(private val accountRepository: AccountRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            (HomeViewModel(accountRepository) as T)
    }

}