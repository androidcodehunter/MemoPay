package com.memo.pay.ui.home

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountRepository

class HomeViewModel(private val accountRepository: AccountRepository): ViewModel() {
    private var amountLiveData = MutableLiveData<Pair<Double, String>>()
    private var myAccount: Account? = null

    fun setMyAccount(account: Account){
        myAccount = account
    }

    fun isAmountValid(amount: Double): Boolean {
        if (amount.toString().isNullOrEmpty())return false
        myAccount?.let {
            if (amount <= it.balance){
                return true
            }
        }
        return false
    }

    fun getTransactions(forceReload: Boolean, accountNumber: String): LiveData<Result<List<Transaction>>> {
       return liveData {
           emit(Result.Loading)
            val transactionResult = accountRepository.getTransactionsHistory(true, accountNumber)
           if (transactionResult is Result.Success){
               emit(Result.Success(transactionResult.data))
           }else if (transactionResult is Result.Error){
               emit(Result.Error(transactionResult.exception))
           }
        }
    }


    fun getAccount(forceReload: Boolean, accountNumber: String): LiveData<Result<Account>> {
        return liveData {
            emit(Result.Loading)
            val accountResult = accountRepository.getAccount(forceReload, accountNumber)
            if (accountResult is Result.Success){
                emit(Result.Success(accountResult.data))
            }else if (accountResult is Result.Error){
                emit(Result.Error(accountResult.exception))
            }
        }
    }

    fun setAmount(amount: String, accountNumber: String){
         if (amount.isDigitsOnly() && amount.isNotEmpty()){
             amountLiveData.value = Pair(amount.toDouble(), accountNumber)
        }else amountLiveData.value = Pair(0.0, "")
    }

    fun getAmount() = amountLiveData

    fun addMoney(amount: Double, accountNumber: String): LiveData<Result<Account>> {
        return liveData {
            emit(Result.Loading)
            val accountResult = accountRepository.addMoney(amount, accountNumber)
            if (accountResult is Result.Success){
                emit(Result.Success(accountResult.data))
            }else if (accountResult is Result.Error){
                emit(Result.Error(accountResult.exception))
            }
        }
    }

    fun getFrequentContacts(): LiveData<Result<List<Account>>> {
        return liveData {
            emit(Result.Loading)
            val frequentContactResponse = accountRepository.getFrequentContacts()
            if (frequentContactResponse is Result.Success){
                emit(Result.Success(frequentContactResponse.data))
            }else if (frequentContactResponse is Result.Error){
                emit(Result.Error(frequentContactResponse.exception))
            }
        }
    }

    fun getContacts(favContactTitle: String, otherContactTitle: String): LiveData<Result<List<Any>>> {
        return liveData {
            emit(Result.Loading)
            val contactResponse = accountRepository.getContacts()
            if (contactResponse is Result.Success){
                val list = mutableListOf<Any>()
                list.add(favContactTitle)
                list.addAll(contactResponse.data.filter { it.isFavorite })
                list.add(otherContactTitle)
                list.addAll(contactResponse.data.filter { !it.isFavorite })
                emit(Result.Success(list.toList()))
            }else if (contactResponse is Result.Error){
                emit(Result.Error(contactResponse.exception))
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class HomeViewModelFactory(private val accountRepository: AccountRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            (HomeViewModel(accountRepository) as T)
    }

}