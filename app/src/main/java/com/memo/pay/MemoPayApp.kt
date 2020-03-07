package com.memo.pay

import android.app.Application
import com.memo.pay.di.netWorkModule
import com.memo.pay.di.notificationModule
import com.memo.pay.di.repositoryModule
import com.memo.pay.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class MemoPayApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        startKoinForDependency()
    }

    /**
     * Initialize all dependencies here.
     * All dependency related code available in @di package.
     */
    private fun startKoinForDependency() {
        startKoin {
            androidContext(this@MemoPayApp)
            modules(listOf(netWorkModule,
                repositoryModule,
                viewModelModule,
                notificationModule))
        }
    }

}