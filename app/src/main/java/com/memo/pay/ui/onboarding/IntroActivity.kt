package com.memo.pay.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager2.widget.ViewPager2
import com.memo.pay.R
import com.memo.pay.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private val indicators = mutableListOf<AppCompatImageView>()
    private lateinit var slidingAdapter: ScreenSlidePagerAdapter
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        indicators.add(intro_indicator_0)
        indicators.add(intro_indicator_1)
        indicators.add(intro_indicator_2)

         slidingAdapter = ScreenSlidePagerAdapter(this, mutableListOf("", "", ""))

        introViewPager.adapter = slidingAdapter
        introViewPager.currentItem = currentPage
        updateIndicators(currentPage)
        initListeners()
    }

    private fun initListeners() {

        introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

                /*              /*
                color update
                 */
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);*/

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                updateIndicators(position)
                when (position) {
                    0 -> {
                        //introViewPager.setBackgroundColor()
                    }
                    1 -> {
                        //introViewPager.setBackgroundColor()
                    }
                    2 -> {
                        // introViewPager.setBackgroundColor()
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

        intro_btn_skip.setOnClickListener { finish() }

        intro_btn_finish.setOnClickListener {
            MainActivity.startMainActivity(this)
            finish()
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in indicators.indices) {
            indicators[i].setBackgroundResource(
                if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
        }
    }

    companion object{
        fun startIntroActivity(context: Context){
            context.startActivity(Intent(context, IntroActivity::class.java))
        }
    }

}
