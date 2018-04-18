package com.ericyl.utils.util

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun View.showSoftInput() {
    if (this is EditText) {
        requestFocus()
        isCursorVisible = true
    }
    val inputMethodManager = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.hideSoftInput() {
    val inputMethodManager = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isActive)
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSoftInputDelay(delayMillis: Long) {
    Handler().postDelayed({ showSoftInput() }, delayMillis)
}

fun View.hideSoftInputDelay(delayMillis: Long) {
    Handler().postDelayed({ hideSoftInput() }, delayMillis)
}
