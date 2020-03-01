package com.memo.pay.data.source

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction

class AccountRepositoryImpl(private val accountLocalDataSource: AccountDataSource,
                            private val accountRemoteDataSource: AccountDataSource){

    suspend fun getTransactionsHistory(forceUpdate: Boolean = false): Result<List<Transaction>> {
        if (forceUpdate){
            updateTransactionsFromRemoteDataSource()
        }
        return accountLocalDataSource.getTransactionsHistory()
    }

    private suspend fun updateTransactionsFromRemoteDataSource() {
        val remoteTransactions = accountRemoteDataSource.getTransactionsHistory()

        if (remoteTransactions is Result.Success) {
            // Real apps might want to do a proper sync.
            //accountLocalDataSource.saveTransactions(remoteTransactions.data)
        } else if (remoteTransactions is Result.Error) {
            throw remoteTransactions.exception
        }
    }


     suspend fun getAccount(forceUpdate: Boolean = false): Result<Account> {
        if (forceUpdate) {
            try {
                updateAccountFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return accountLocalDataSource.getAccount("")
    }

    private suspend fun updateAccountFromRemoteDataSource() {
        val remoteAccount = accountRemoteDataSource.getAccount("")

        if (remoteAccount is Result.Success) {
            // Real apps might want to do a proper sync.
           /// accountLocalDataSource.saveAccount()
        } else if (remoteAccount is Result.Error) {
            throw remoteAccount.exception
        }
    }

}