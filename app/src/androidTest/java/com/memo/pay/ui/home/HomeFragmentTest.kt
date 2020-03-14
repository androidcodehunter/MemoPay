package com.memo.pay.ui.home

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.memo.pay.R
import com.memo.pay.data.source.AccountRepository
import com.memo.pay.ui.interfaces.OnToolbarChangeListener
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeFragmentTest{

    @Before
    fun initRepository(){}

    @Test
    fun homeActivity_DisplayMemoPayBalanceInUi() = runBlocking{

        val onToolbarChangeListener = mock(OnToolbarChangeListener::class.java)
        val navController = mock(NavController::class.java)
        val homeFragmentScenario = launchFragmentInContainer(Bundle(), R.style.AppTheme) {
            HomeFragment(onToolbarChangeListener)
        }

        homeFragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
            it.arguments = Bundle()
        }

        onView(withId(R.id.tvAccountBalance)).check(matches(isDisplayed()))
       // onView(withId(R.id.tvAccountBalance)).check(matches(withText("AED 1500.00")))
        //Sleeping the fragment few seconds to see the ui visible.
        ///Thread.sleep(2000)
    }

    @Test
    fun clickAddMoneyButton_navigateToAddMoneyFragment(){
        val onToolbarChangeListener = mock(OnToolbarChangeListener::class.java)
        val navController = mock(NavController::class.java)
        val homeFragmentScenario = launchFragmentInContainer(Bundle(), R.style.AppTheme) {
            HomeFragment(onToolbarChangeListener)
        }

        homeFragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.tvAddMoney)).perform(click())
        verify(navController).navigate(R.id.action_homeFragment_to_AddMoneyFragment)
    }

    @Test
    fun clickSendMoneyButton_navigateToSendMoneyFragment(){
        val onToolbarChangeListener = mock(OnToolbarChangeListener::class.java)
        val navController = mock(NavController::class.java)
        val homeFragmentScenario = launchFragmentInContainer(Bundle(), R.style.AppTheme) {
            HomeFragment(onToolbarChangeListener)
        }

        homeFragmentScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.tvSendMoney)).perform(click())
        verify(navController).navigate(R.id.action_homeFragment_to_sendMoneyFragment)
    }

}