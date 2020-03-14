package com.memo.pay.ui.home

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.memo.pay.R
import com.memo.pay.di.netWorkModule
import com.memo.pay.di.repositoryModule
import com.memo.pay.di.viewModelModule
import com.memo.pay.ui.interfaces.OnToolbarChangeListener
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeFragmentTest : AutoCloseKoinTest() {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initRepository() {
        stopKoin()

        startKoin {
            modules(
                listOf(
                    netWorkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
            androidContext(ApplicationProvider.getApplicationContext())
        }
    }

    @Test
    fun homeActivity_DisplayMemoPayBalanceInUi() = runBlocking {
        val onToolbarChangeListener = mock(OnToolbarChangeListener::class.java)
        val navController = mock(NavController::class.java)
        val homeFragmentScenario = launchFragmentInContainer(
            Bundle(),
            R.style.AppTheme
        ) {
            HomeFragment(onToolbarChangeListener)
        }

        homeFragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
            it.arguments = Bundle()
        }

        onView(withId(R.id.tvAccountBalance))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickAddMoneyButton_navigateToAddMoneyFragment() {
        val onToolbarChangeListener = mock(OnToolbarChangeListener::class.java)
        val navController = mock(NavController::class.java)
        val homeFragmentScenario = launchFragmentInContainer(
            Bundle(),
            R.style.AppTheme
        ) {
            HomeFragment(onToolbarChangeListener)
        }

        homeFragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.tvAddMoney)).perform(click())
        verify(navController).navigate(R.id.action_homeFragment_to_AddMoneyFragment)
    }

    @Test
    fun clickSendMoneyButton_navigateToSendMoneyFragment() {
        val onToolbarChangeListener = mock(OnToolbarChangeListener::class.java)
        val navController = mock(NavController::class.java)
        val homeFragmentScenario = launchFragmentInContainer(
            Bundle(),
            R.style.AppTheme
        ) {
            HomeFragment(onToolbarChangeListener)
        }

        homeFragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.tvSendMoney)).perform(click())
        verify(navController).navigate(R.id.action_homeFragment_to_sendMoneyFragment)
    }

}