package com.memo.pay.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            IntroActivity.startIntroActivity(this@SplashActivity)
            //MainActivity.startMainActivity(this@SplashActivity)
            finish()
        }
    }

    companion object{
        const val DELAY_ONE_SECONDS = 1000L
    }

}
