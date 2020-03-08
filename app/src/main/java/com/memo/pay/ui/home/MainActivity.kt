package com.memo.pay.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI
import com.memo.pay.R
import com.memo.pay.extensions.hideKeyboard
import com.memo.pay.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(){
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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

    companion object{
        fun startMainActivity(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
