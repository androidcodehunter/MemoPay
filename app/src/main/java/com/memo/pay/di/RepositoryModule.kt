package com.memo.pay.di

import com.memo.pay.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
  factory { AppDatabase(androidContext()) }
 /*   factory { HomeRepository(get()) }
    factory { MyTourRepository(get()) }*/
}