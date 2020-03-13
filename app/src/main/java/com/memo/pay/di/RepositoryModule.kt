package com.memo.pay.di

import android.content.Context
import com.memo.pay.data.db.AppDatabase
import com.memo.pay.data.source.AccountRepositoryImpl
import com.memo.pay.data.source.local.AccountLocalDataSource
import com.memo.pay.data.source.remote.AccountRemoteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory { AccountLocalDataSource(get()) }
    factory { AccountRemoteDataSource(get()) }
    factory { provideDatabase(androidContext()) }
    single { AccountRepositoryImpl(get() as AccountLocalDataSource, get() as AccountRemoteDataSource) }
}

fun provideDatabase(androidContext: Context): AppDatabase {
    return AppDatabase(androidContext)
}
