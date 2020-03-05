package com.memo.pay.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment


fun Fragment.hideKeyboard(){
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard(){
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager?.let {
        val networkInfo = it.activeNetworkInfo
        networkInfo?.let { info -> if (info.isConnected) return true }
    }
    return false
}