package com.andrnhd.utils

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import kotlin.properties.Delegates

/**
 * Created by nhd on 2017/4/15.
 */
abstract class BaseActivity : OnClickListener, AppCompatActivity() {
    companion object {
        var mContext: Activity by Delegates.notNull()
        var saveBundle: Bundle by Delegates.notNull()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindLayout())
        saveBundle = savedInstanceState!!
        mContext = this
        init(intent.extras)
        initListener()
        initData()
    }

    abstract fun bindLayout(): Int
    abstract fun init(bundle: Bundle)
    abstract fun initListener()
    abstract fun initData(vararg obj: Any)
    abstract fun onClick(id: Int)

    override fun onClick(v: View?) {
        onClick(v!!.id)
    }


}
