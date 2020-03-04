package com.memo.pay.data

import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.data.source.AccountRepository
import kotlinx.coroutines.runBlocking

class FakeTestAccountRepository : AccountRepository {

    var transactionsServiceData: LinkedHashMap<String, Transaction> = LinkedHashMap()

    override suspend fun getTransactionsHistory(forceUpdate: Boolean): Result<List<Transaction>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAccount(forceUpdate: Boolean, accountNumber: String): Result<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addTransactions(vararg transactions: Transaction){
        for (transaction in transactions){
            transactionsServiceData[transaction.id] = transaction
        }

        runBlocking {
            //call suspend function for observable.
        }
    }
}