package com.memo.pay.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.memo.pay.data.db.dao.AccountDao
import com.memo.pay.data.db.dao.TransactionDao
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
/*TODO singleton database class using room for storing local data. */
@Database(entities = [Account::class, Transaction::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
                buildDatabase(context)
                    .also { instance = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "memopay.db").build()
    }
}