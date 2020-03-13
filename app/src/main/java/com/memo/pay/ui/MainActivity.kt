package com.memo.pay.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.memo.pay.R
import com.memo.pay.extensions.hideKeyboard
import com.memo.pay.ui.home.HomeFragment
import com.memo.pay.ui.interfaces.OnToolbarChangeListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), OnToolbarChangeListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory =
            HomeActivityFactory(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(toolbar_main, false)
        // Find this Fragment's NavController
        navController = findNavController(R.id.nav_host_fragment)
        // Update action bar to reflect navigation
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        navController.navigateUp()
        return super.onOptionsItemSelected(item)
    }

    override fun onToolbarChange() {
        toolbar_main.apply {
            title = getString(R.string.memo_pay_balance)
            setTitleTextColor(ContextCompat.getColor(context, android.R.color.white))
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.windowBackgroundColor))
            findViewById<AppCompatTextView>(R.id.tvAccountIcon).visibility = VISIBLE
        }

    }

    override fun showHideToolbar(show: Boolean) {
        if (show){
            toolbar_main.visibility = VISIBLE
            tvAccountIcon.visibility = VISIBLE
        }else{
            toolbar_main.visibility = View.GONE
        }

    }


    companion object{
        fun startMainActivity(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    class HomeActivityFactory(
        private val onToolbarChangeListener: OnToolbarChangeListener
    ) : FragmentFactory() {
        override fun instantiate(
            classLoader: ClassLoader,
            className: String
        ) = when (className) {
            HomeFragment::class.java.name -> HomeFragment(
                onToolbarChangeListener
            )
            else -> super.instantiate(classLoader, className)
        }
    }


}
