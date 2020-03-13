package com.memo.pay.ui.viewmodel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountRepository
import com.memo.pay.utils.Constants.ACCOUNT_TYPE_SENT
import com.memo.pay.utils.Constants.CURRENCY_AED
import com.memo.pay.utils.Constants.CURRENT_ACCOUNT_NUMBER
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val accountRepository: AccountRepository): ViewModel() {
    private var amountLiveData = MutableLiveData<Pair<Double, String>>()
    private var myAccount: Account? = null

    fun setMyAccount(account: Account){
        myAccount = account
    }

    fun isAmountValid(amount: Double): Boolean {
        if (amount.toString().isNullOrEmpty() || amount.toString().toDouble() <= 0)return false
        myAccount?.let {
            if (amount <= it.balance){
                return true
            }
        }
        return false
    }

    fun getTransactions(forceReload: Boolean, accountNumber: String): LiveData<Result<List<Any>>> {
       return liveData {
           emit(Result.Loading)
            val transactionResult = accountRepository.getTransactionsHistory(true, accountNumber)
           if (transactionResult is Result.Success){
               var formatter = SimpleDateFormat("dd MMMM")
               val transactionMap = transactionResult.data.groupBy { formatter.format(it.date) }

               val transactionList = mutableListOf<Any>()
               transactionMap.keys.forEach { key ->
                //   var formatter = SimpleDateFormat("dd-MMMM")
                  /// var formattedDate = formatter.format(key)
                   transactionList.add(key)
                   val transactions = transactionMap[key]
                   transactions?.forEach {
                       transactionList.add(it)
                   }
               }
               emit(Result.Success(transactionList.toList()))
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

    fun addMoney(amount: Double, accountNumber: String): LiveData<Result<AddMoney>> {
        return liveData {
            emit(Result.Loading)
            val accountResult = accountRepository.addMoney(amount, accountNumber)
            if (accountResult is Result.Success){
                emit(Result.Success(
                    AddMoney(
                        amount,
                        accountResult.data
                    )
                ))
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

    fun sendMoney(amount: Double, senderAccountNumber: String, receiverAccount: Account): LiveData<Result<Transaction>> {
        val transaction = Transaction(receiverAccount.name, ACCOUNT_TYPE_SENT, amount, CURRENCY_AED, receiverAccount.profilePic, Date(), CURRENT_ACCOUNT_NUMBER, receiverAccount.accountNumber)
        return liveData {
            emit(Result.Loading)
            val sendMoneyResponse = accountRepository.sendMoney(transaction)
            if (sendMoneyResponse is Result.Success){
                emit(Result.Success(sendMoneyResponse.data))
            }else if (sendMoneyResponse is Result.Error){
                emit(Result.Error(sendMoneyResponse.exception))
            }
        }
    }


    data class AddMoney(val amount: Double, val account: Account)

    @Suppress("UNCHECKED_CAST")
    class HomeViewModelFactory(private val accountRepository: AccountRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            (HomeViewModel(accountRepository) as T)
    }

}