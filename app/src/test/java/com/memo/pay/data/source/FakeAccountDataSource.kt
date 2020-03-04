package com.memo.pay.data.source

import com.memo.pay.data.Result
import com.memo.pay.data.Result.Error
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction

class FakeAccountDataSource(var transactions: MutableList<Transaction>? = mutableListOf(),
                            var accounts: MutableList<Account>? = mutableListOf()): AccountDataSource {

    override suspend fun saveAccount(account: Account) {
        accounts?.add(account)
    }

    override suspend fun getAccount(accountNumber: String): Result<Account> {
        accounts?.let {
            for (account in it) {
                if (account.accountNumber == accountNumber)return Result.Success(account)
            }
        }

        return Error(Exception("No Account Found"))
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        transactions?.add(transaction)
    }

    override suspend fun getTransactionsHistory(accountNumber: String): Result<List<Transaction>> {
        transactions?.let { return Result.Success(it.toList()) }
        return Error(Exception("Tasks not found"))
    }

    override suspend fun saveTransactions(transactions: List<Transaction>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}