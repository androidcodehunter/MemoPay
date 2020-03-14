package com.memo.pay.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.memo.pay.R
import com.memo.pay.data.source.AccountRepositoryImpl
import com.memo.pay.di.netWorkModule
import com.memo.pay.di.repositoryModule
import com.memo.pay.di.viewModelModule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.AutoCloseKoinTest

/*TODO end to end test to automate the activity flow using instrumentation test. */
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

    @Test
    fun addMoneyFlowTest() = runBlocking{

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)


        Espresso.onView(ViewMatchers.withId(R.id.tvAddMoney)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.etEnterAmount))
            .perform(ViewActions.replaceText("10.00"))

        Espresso.onView(ViewMatchers.withId(R.id.btnTopUP)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.tvAccountBalance))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        activityScenario.close()

    }

    @Test
    fun sendMoneyFlowTest(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.tvSendMoney)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.etEnterAmount))
            .perform(ViewActions.replaceText("10.00"))
    }


}