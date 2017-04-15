package com.andrnhd.utils

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.widget.Toast

/**
 * Created by nhd on 2017/4/15.
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this, message, duration)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun CoordinatorLayout.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this,message,duration).show()
}