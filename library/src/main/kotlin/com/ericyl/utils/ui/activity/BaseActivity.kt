package com.ericyl.utils.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * @author ericyl
 * 2017/7/31
 */
abstract class BaseActivity(private val showAction: Boolean = true) : AppCompatActivity(), View.OnClickListener {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (showAction)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)

}