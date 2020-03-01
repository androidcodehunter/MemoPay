package com.memo.pay.di

import com.memo.pay.data.db.AppDatabase
import com.memo.pay.data.source.AccountRepository
import com.memo.pay.data.source.AccountRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
   factory { AppDatabase(androidContext()) }
  factory { AccountRepository() }
   factory { AccountRepositoryImpl() }
  /* factory { MyTourRepository(get()) }*/
}