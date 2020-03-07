package com.memo.pay.data.db.dao

import androidx.room.*

@Dao
interface TransactionDao {

    @Query("select * from transactions where senderAccountNumber =:senderAccountNumber")
    suspend fun getTransactions(senderAccountNumber: String): List<com.memo.pay.data.db.table.Transaction>

    @Query("select * from transactions where id =:transactionId")
    suspend fun getTransaction(transactionId: String): com.memo.pay.data.db.table.Transaction

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransactions(transactions: List<com.memo.pay.data.db.table.Transaction>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: com.memo.pay.data.db.table.Transaction)
}


