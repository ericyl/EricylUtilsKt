package com.ericyl.utils.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView

import com.ericyl.utils.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2sp
import org.jetbrains.anko.sp

class CustomSearchView(context: Context, attrs: AttributeSet, defStyleAttr: Int) : SearchView(context, attrs, defStyleAttr) {

    private var searchAutoComplete: SearchView.SearchAutoComplete

    var doCloseAction: (() -> Unit)? = null

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchView, defStyleAttr, R.style.defaultStyle_CustomSearchView)

        try {
            var editTextSize = sp(16f)
            if (a.hasValue(R.styleable.CustomSearchView_custom_editTextSize))
                editTextSize = a.getDimension(R.styleable.CustomSearchView_custom_editTextSize, sp(16f).toFloat()).toInt()

            val imgGo = a.getDrawable(R.styleable.CustomSearchView_custom_imgGo)

            var goPaddingLeft = dip(4.0f)
            if (a.hasValue(R.styleable.CustomSearchView_custom_goPaddingLeft))
                goPaddingLeft = dip(a.getDimension(R.styleable.CustomSearchView_custom_goPaddingLeft, 4.0f))

            var goPaddingRight = dip(4.0f)
            if (a.hasValue(R.styleable.CustomSearchView_custom_goPaddingRight))
                goPaddingRight = dip(a.getDimension(R.styleable.CustomSearchView_custom_goPaddingRight, 4.0f))

            var searchPlateBackground = Color.TRANSPARENT
            if (a.hasValue(R.styleable.CustomSearchView_custom_searchPlateBackground))
                searchPlateBackground = a.getColor(R.styleable.CustomSearchView_custom_searchPlateBackground, Color.TRANSPARENT)

            var submitAreaBackground = Color.TRANSPARENT
            if (a.hasValue(R.styleable.CustomSearchView_custom_submitAreaBackground))
                submitAreaBackground = a.getColor(R.styleable.CustomSearchView_custom_submitAreaBackground, Color.TRANSPARENT)

            searchAutoComplete = findViewById(androidx.appcompat.R.id.search_src_text)
            searchAutoComplete.textSize = px2sp(editTextSize)

            isSubmitButtonEnabled = true

            val btnGo = findViewById<ImageView>(androidx.appcompat.R.id.search_go_btn)
            if (imgGo == null)
                btnGo.setImageResource(R.drawable.ic_search_query)
            else
                btnGo.setImageDrawable(imgGo)

            btnGo.setPadding(goPaddingLeft, 0, goPaddingRight, 0)

            findViewById<View>(androidx.appcompat.R.id.search_plate).setBackgroundColor(searchPlateBackground)
            findViewById<View>(androidx.appcompat.R.id.submit_area).setBackgroundColor(submitAreaBackground)
        } finally {
            a.recycle()
        }
    }


    fun setText(text: String) {
        searchAutoComplete.setText(text)
    }

    private fun createDefaultColorStateList(baseColorThemeAttr: Int): ColorStateList? {
        val value = TypedValue()
        if (!context.theme.resolveAttribute(baseColorThemeAttr, value, true)) {
            return null
        }
        val baseColor = AppCompatResources.getColorStateList(
                context, value.resourceId)
        if (!context.theme.resolveAttribute(
                        androidx.appcompat.R.attr.colorPrimary, value, true)) {
            return null
        }
        val colorPrimary = value.data
        val defaultColor = baseColor.defaultColor
        return ColorStateList(arrayOf(DISABLED_STATE_SET, CHECKED_STATE_SET, View.EMPTY_STATE_SET), intArrayOf(baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor))
    }


    override fun dispatchKeyEventPreIme(event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                doCloseAction?.invoke()
            }
        }
        return super.dispatchKeyEventPreIme(event)
    }


    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
        private val DISABLED_STATE_SET = intArrayOf(-android.R.attr.state_enabled)
    }
}
