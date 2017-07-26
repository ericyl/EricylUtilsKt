package com.ericyl.utils.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by ericyl on 2017/7/24.
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)

}