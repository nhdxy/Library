package com.andrnhd.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by nhd on 2017/4/15.
 */
fun <T> Context.skit(clazz: Class<T>, bundle: Bundle = Bundle(), isFinished: Boolean = false) {
    when (this) {
        is Activity -> {
            val intent = Intent(this, clazz)
            intent.putExtras(bundle)
            this.startActivity(intent)
            if (isFinished) {
                this.finish()
            }
        }
        is Fragment -> {
            val intent = Intent(this, clazz)
            intent.putExtras(bundle)
            this.activity.startActivity(intent)
            if (isFinished) {
                this.activity.finish()
            }
        }
    }
}

fun <T> Context.skitForResult(clazz: Class<T>, requestCode: Int, bundle: Bundle = Bundle()) {
    when (this) {
        is Activity -> {
            val intent = Intent(this, clazz)
            intent.putExtras(bundle)
            this.startActivityForResult(intent, requestCode)
        }
        is Fragment -> {
            val intent = Intent(this, clazz)
            intent.putExtras(bundle)
            this.startActivityForResult(intent, requestCode)
        }
    }
}