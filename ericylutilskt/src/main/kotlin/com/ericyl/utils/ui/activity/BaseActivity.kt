package com.ericyl.utils.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

/**
 * @author ericyl on 2017/7/31
 */
abstract class BaseActivity(private val showAction: Boolean = true) : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (showAction)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}