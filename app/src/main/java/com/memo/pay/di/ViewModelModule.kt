package com.memo.pay.di

import com.memo.pay.ui.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule= module {
    factory { HomeViewModel(get()) }
   /* factory { MyToursViewModel(get()) }*/
}