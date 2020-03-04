package com.memo.pay.data.db.dao

import androidx.room.*
import com.memo.pay.data.db.table.Account

@Dao
interface AccountDao{

    @Query("select * from account where accountNumber =:accountNumber")
    suspend fun getAccount(accountNumber: String): Account

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAccount(account: Account)

    @Transaction
    @Query("UPDATE account SET balance =:newBalance WHERE accountNumber =:accountNumber")
    suspend fun updateBalance(newBalance: Double, accountNumber: String)
}