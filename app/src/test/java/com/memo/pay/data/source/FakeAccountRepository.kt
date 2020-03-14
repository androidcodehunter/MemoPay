package com.memo.pay.data.source

import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction

class FakeAccountRepository: AccountRepository {

    /*TODO change this variable to check error cases for fake repository or you can set the value from caller function. */
    private var shouldReturnError = false

    private var TRANSACTIONS_SERVICE_DATA: LinkedHashMap<Int, Transaction> = LinkedHashMap()
    private var ACCOUNT_SERVICE_DATA :  LinkedHashMap<String, Account> = LinkedHashMap()

    fun setReturnTrue(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getTransactionsHistory(forceUpdate: Boolean, accountNumber: String): Result<List<Transaction>> {

        if (shouldReturnError){
            return Result.Error(Exception("No Transactions Available with this account."))
        }

        val allTransactions = TRANSACTIONS_SERVICE_DATA.values.toList()
        val transactionsByAccount = allTransactions.filter{ it.senderAccountNumber == accountNumber }.toList()

        if (transactionsByAccount.isNullOrEmpty()){
            return Result.Error(Exception("No Transactions Available with this account."))
        }
        return Result.Success(transactionsByAccount)
    }

    override suspend fun getAccount(forceUpdate: Boolean, accountNumber: String): Result<Account> {
        if (shouldReturnError){
            return Result.Error(Exception("Cannot find any account data."))
        }
        ACCOUNT_SERVICE_DATA[accountNumber]?.let {
            return Result.Success(it)
        }

        return Result.Error(Exception("Cannot find any account data."))
    }

    override suspend fun addMoney(amount: Double, accountNumber: String): Result<Account> {
        val account = getAccount(true, accountNumber)
        if (account is Result.Error){
            return Result.Error(Exception("Add money is not possible"))
        }else if (account is Result.Success){
            ACCOUNT_SERVICE_DATA[accountNumber]?.apply {
                balance = account.data.balance + amount
                return Result.Success(this)
            }
        }
        return Result.Error(Exception("Add money is not possible"))
    }

    override suspend fun getFrequentContacts(): Result<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun getContacts(): Result<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMoney(transaction: Transaction): Result<Transaction> {

        val account = getAccount(true, transaction.senderAccountNumber)
        if (account is Result.Error){
            return Result.Error(Exception("Account is not valid, try again"))
        }else if (account is Result.Success){
            //update balance
            ACCOUNT_SERVICE_DATA[account.data.accountNumber]?.let {
                //update balance
                it.balance -= transaction.transactionAmount
                ACCOUNT_SERVICE_DATA[account.data.accountNumber] = it
                TRANSACTIONS_SERVICE_DATA[transaction.id] = transaction
                return Result.Success(transaction)
            }
        }

        return Result.Error(Exception("Transfer money is not possible, try again"))
    }

    override suspend fun saveAccount(account: Account) {
        ACCOUNT_SERVICE_DATA[account.accountNumber] = account
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        TRANSACTIONS_SERVICE_DATA[transaction.id] = transaction
    }

    override suspend fun saveTransactions(transactions: List<Transaction>) {
        transactions.forEach {
            saveTransaction(it)
        }
    }

}