package com.memo.pay.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

fun Fragment.getFragmentNavController(@IdRes id: Int): NavController? {
    return activity?.let {
        return@let Navigation.findNavController(it, id)
    }
}