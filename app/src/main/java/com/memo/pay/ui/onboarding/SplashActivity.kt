package com.memo.pay.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.memo.pay.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntroActivity.startIntroActivity(this)
    }
}
