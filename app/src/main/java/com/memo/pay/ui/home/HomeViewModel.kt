package com.memo.pay.ui.home

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountRepository

class HomeViewModel(private val accountRepository: AccountRepository): ViewModel() {
    private var amountLiveData = MutableLiveData<Pair<Double, String>>()

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

    @Suppress("UNCHECKED_CAST")
    class HomeViewModelFactory(private val accountRepository: AccountRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            (HomeViewModel(accountRepository) as T)
    }

}