package com.memo.pay.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.memo.pay.ui.home.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchOnboardingAfterOneSeconds()
    }

    private fun launchOnboardingAfterOneSeconds() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(DELAY_ONE_SECONDS)
            MainActivity.startMainActivity(this@SplashActivity)
        }
    }

    companion object{
        const val DELAY_ONE_SECONDS = 1000L
    }

}
