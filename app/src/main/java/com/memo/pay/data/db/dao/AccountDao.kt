package com.memo.pay.data.db.dao

import androidx.room.*
import com.memo.pay.data.db.table.Account

@Dao
interface AccountDao{

    @Query("select * from account")
    suspend fun getAccount(): Account

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccount(account: Account)
}