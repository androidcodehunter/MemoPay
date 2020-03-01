package com.memo.pay.data.db.dao

import androidx.room.*

@Dao
interface TransactionDao {

    @Query("select * from transactions")
    suspend fun getTransactions(): List<com.memo.pay.data.db.table.Transaction>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransactions(transactions: List<com.memo.pay.data.db.table.Transaction>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: com.memo.pay.data.db.table.Transaction)
}


