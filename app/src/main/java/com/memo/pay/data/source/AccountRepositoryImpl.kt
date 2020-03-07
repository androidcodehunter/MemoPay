package com.memo.pay.data.source

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import timber.log.Timber

class AccountRepositoryImpl(private val accountLocalDataSource: AccountDataSource,
                            private val accountRemoteDataSource: AccountDataSource) :
    AccountRepository {

    override suspend fun getTransactionsHistory(forceUpdate: Boolean, accountNumber: String): Result<List<Transaction>> {
        if (forceUpdate){
            updateTransactionsFromRemoteDataSource(accountNumber)
        }
        return accountLocalDataSource.getTransactionsHistory(accountNumber)
    }

    private suspend fun updateTransactionsFromRemoteDataSource(accountNumber: String) {
        val remoteTransactions = accountRemoteDataSource.getTransactionsHistory(accountNumber)
        if (remoteTransactions is Result.Success) {
            accountLocalDataSource.saveTransactions(remoteTransactions.data)
        } else if (remoteTransactions is Result.Error) {
            throw remoteTransactions.exception
        }
    }

     override suspend fun getAccount(forceUpdate: Boolean, accountNumber: String): Result<Account> {
        if (forceUpdate) {
            try {
                updateAccountFromRemoteDataSource(accountNumber)
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return accountLocalDataSource.getAccount(accountNumber)
    }

    /*TODO add money to local data source and get the updated account response in the channel upward*/
    override suspend fun addMoney(amount: Double, accountNumber: String): Result<Account> {
        val addMoneyResponse = accountRemoteDataSource.addMoney(amount, accountNumber)
        if (addMoneyResponse is Result.Success){
            accountLocalDataSource.addMoney(addMoneyResponse.data.balance, addMoneyResponse.data.accountNumber)
        }else if (addMoneyResponse is Result.Error){
            throw addMoneyResponse.exception
        }
        return accountLocalDataSource.getAccount(accountNumber)
    }

    private suspend fun updateAccountFromRemoteDataSource(accountNumber: String) {
        val remoteAccount = accountRemoteDataSource.getAccount(accountNumber)
        if (remoteAccount is Result.Success) {
           accountLocalDataSource.saveAccount(remoteAccount.data)
        } else if (remoteAccount is Result.Error) {
            throw remoteAccount.exception
        }
    }

    override suspend fun getFrequentContacts(): Result<List<Account>> = accountRemoteDataSource.getFrequentContacts()

    override suspend fun getContacts(): Result<List<Account>> = accountRemoteDataSource.getContacts()

    override suspend fun sendMoney(transaction: Transaction): Result<Transaction> {
        Timber.d("send money call")
        val transactionResponse = accountRemoteDataSource.sendMoney(transaction)
        Timber.d("send money ${transactionResponse}")
        if (transactionResponse is Result.Success){
            accountLocalDataSource.sendMoney(transactionResponse.data)
        }
        return transactionResponse
    }


}