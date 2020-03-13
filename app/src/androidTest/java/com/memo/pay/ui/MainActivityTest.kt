package com.memo.pay.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.memo.pay.data.source.AccountRepositoryImpl
import com.memo.pay.di.netWorkModule
import com.memo.pay.di.repositoryModule
import com.memo.pay.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.AutoCloseKoinTest


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest: AutoCloseKoinTest() {

    val accountRepository: AccountRepositoryImpl by inject()

    @Before
    fun before(){
        stopKoin()

        startKoin {

            modules(listOf(
                netWorkModule,
                repositoryModule,
                viewModelModule
            ))
            androidContext(ApplicationProvider.getApplicationContext())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun addMoneyTest() = runBlocking {

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        accountRepository.getContacts()
        activityScenario.close()

    }

}