package com.memo.pay.ui.onboarding

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fragmentActivity: FragmentActivity, val list: List<String>) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = list.size
    override fun createFragment(position: Int) = SlidingFragment.newInstance(position)
}
