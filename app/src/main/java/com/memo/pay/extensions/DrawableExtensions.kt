package com.memo.pay.extensions

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build

fun Drawable.setColor(color: Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}