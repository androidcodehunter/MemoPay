package com.memo.pay.ui.onboarding

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.memo.pay.R
import com.memo.pay.ui.home.MainActivity
import com.memo.pay.utils.Constants.getIntroImages
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private val indicators = mutableListOf<AppCompatImageView>()
    private val titles = mutableListOf<String>()
    private lateinit var slidingAdapter: ScreenSlidePagerAdapter
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        indicators.add(intro_indicator_0)
        indicators.add(intro_indicator_1)
        indicators.add(intro_indicator_2)
        titles.add(getString(R.string.intro_connect_bank_account))
        titles.add(getString(R.string.intro_send_money_instantly))
        titles.add(getString(R.string.intro_no_fees))

        slidingAdapter = ScreenSlidePagerAdapter(this, getIntroImages())
        introViewPager.adapter = slidingAdapter
        introViewPager.currentItem = currentPage
        updateIndicators(currentPage)
        initListeners()
    }

    private fun initListeners() {

        val color1 = ContextCompat.getColor(this, R.color.cyan)
        val color2 = ContextCompat.getColor(this, R.color.orange)
        val color3 = ContextCompat.getColor(this, R.color.green)
        val evaluator = ArgbEvaluator()
        val colorList = intArrayOf(color1, color2, color3)

        introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val colorUpdate = evaluator.evaluate(positionOffset, colorList[position], colorList[if (position == 2) position else position + 1]) as Int
               // introViewPager.setBackgroundColor(colorUpdate)
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                updateIndicators(position)
                when (position) {
                    0 -> {
                       // introViewPager.setBackgroundColor(color1)
                    }
                    1 -> {
                       // introViewPager.setBackgroundColor(color2)
                    }
                    2 -> {
                       // introViewPager.setBackgroundColor(color3)
                    }
                }

                intro_btn_next.visibility = if (position == 2) GONE else VISIBLE
                intro_btn_finish.visibility = if (position == 2) VISIBLE else GONE
            }
        })

        intro_btn_next.setOnClickListener {
            currentPage += 1
            introViewPager.setCurrentItem(currentPage, true)
        }

        intro_btn_skip.setOnClickListener {
            startMainActivity()
        }

        intro_btn_finish.setOnClickListener {
            startMainActivity()
        }

    }

    private fun startMainActivity(){
        MainActivity.startMainActivity(this)
        finish()
    }

    private fun updateIndicators(position: Int) {
        for (i in indicators.indices) {
            indicators[i].setBackgroundResource(
                if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
            tvIntroTitle.text = titles[position]
        }
    }

    companion object{
        fun startIntroActivity(context: Context){
            context.startActivity(Intent(context, IntroActivity::class.java))
        }

    }

}
