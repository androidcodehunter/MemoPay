package com.memo.pay.ui.home

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.memo.pay.R
import com.memo.pay.data.FakeTestAccountRepository
import com.memo.pay.data.source.AccountRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeFragmentTest{

    private lateinit var repository: AccountRepository

    @Before
    fun initRepository(){
        repository = FakeTestAccountRepository()
    }

    @Test
    fun homeActivity_DisplayAccountInUi() = runBlocking{
        /*TODO save account in repository and show it on ui*/
        launchFragmentInContainer<HomeFragment>(Bundle(), R.style.AppTheme)
        Thread.sleep(2000)
    }

}