package com.memo.pay.di

import com.memo.pay.data.source.AccountRepositoryImpl
import com.memo.pay.ui.viewmodel.HomeViewModel
import org.koin.dsl.module

val viewModelModule= module {
    factory { HomeViewModel(get() as AccountRepositoryImpl) }

}