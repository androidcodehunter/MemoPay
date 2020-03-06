package com.memo.pay.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showKeyboard(){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    inputMethodManager.showSoftInput(this, 0)
}

fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

inline fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG){
    val snack = Snackbar.make(this, message, length)
    snack.show()
}
