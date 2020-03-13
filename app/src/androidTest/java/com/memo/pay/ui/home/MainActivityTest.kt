package com.memo.pay.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import com.memo.pay.data.source.AccountRepositoryImpl
import com.memo.pay.di.repositoryModule
import com.memo.pay.di.viewModelModule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest: AutoCloseKoinTest(){

    val repository : AccountRepositoryImpl by inject()


    @Before
    fun init(){
        stopKoin()
        startKoin {
            androidContext(getApplicationContext())
            modules(listOf(repositoryModule, viewModelModule))
        }
    }


    @Test
    fun addMoneyCheck() = runBlocking{

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        val contacts = repository.getContacts()

        ViewMatchers.assertThat("savedAccount", Matchers.notNullValue())
        ViewMatchers.assertThat("savedAccount", Matchers.notNullValue())

        activityScenario.close()

    }


}