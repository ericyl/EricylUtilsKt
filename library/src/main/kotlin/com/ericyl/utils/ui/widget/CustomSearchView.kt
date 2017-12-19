package com.ericyl.utils.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.SearchView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView

import com.ericyl.utils.R
import com.ericyl.utils.util.dp2px
import com.ericyl.utils.util.px2sp
import com.ericyl.utils.util.sp2px

class CustomSearchView(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : SearchView(context, attrs, defStyleAttr) {

    private var searchAutoComplete: SearchView.SearchAutoComplete

    private lateinit var searchViewController: ISearchViewController

    fun setSearchController(searchViewController: ISearchViewController) {
        this.searchViewController = searchViewController
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchView, defStyleAttr, R.style.defaultStyle_CustomSearchView)

        try {
            var editTextSize = 16.0f
            if (a.hasValue(R.styleable.CustomSearchView_custom_editTextSize))
                editTextSize = px2sp(context, a.getDimension(R.styleable.CustomSearchView_custom_editTextSize, sp2px(context, 16.0F, 0.0F)), 0.0F)

            val imgGo = a.getDrawable(R.styleable.CustomSearchView_custom_imgGo)

            var goPaddingLeft = dp2px(context, 4.0F, 0.0F).toInt()
            if (a.hasValue(R.styleable.CustomSearchView_custom_goPaddingLeft))
                goPaddingLeft = a.getDimension(R.styleable.CustomSearchView_custom_goPaddingLeft, 4.0f).toInt()

            var goPaddingRight = dp2px(context, 4.0F, 0.0F).toInt()
            if (a.hasValue(R.styleable.CustomSearchView_custom_goPaddingRight))
                goPaddingRight = a.getDimension(R.styleable.CustomSearchView_custom_goPaddingRight, 4.0f).toInt()

            var searchPlateBackground = Color.TRANSPARENT
            if (a.hasValue(R.styleable.CustomSearchView_custom_searchPlateBackground))
                searchPlateBackground = a.getColor(R.styleable.CustomSearchView_custom_searchPlateBackground, Color.TRANSPARENT)

            var submitAreaBackground = Color.TRANSPARENT
            if (a.hasValue(R.styleable.CustomSearchView_custom_submitAreaBackground))
                submitAreaBackground = a.getColor(R.styleable.CustomSearchView_custom_submitAreaBackground, Color.TRANSPARENT)

            searchAutoComplete = findViewById(android.support.v7.appcompat.R.id.search_src_text)
            searchAutoComplete.textSize = editTextSize

            isSubmitButtonEnabled = true

            val btnGo = findViewById<ImageView>(android.support.v7.appcompat.R.id.search_go_btn)
            if (imgGo == null)
                btnGo.setImageResource(R.drawable.ic_search_query)
            else
                btnGo.setImageDrawable(imgGo)

            btnGo.setPadding(goPaddingLeft, 0, goPaddingRight, 0)

            findViewById<View>(android.support.v7.appcompat.R.id.search_plate).setBackgroundColor(searchPlateBackground)
            findViewById<View>(android.support.v7.appcompat.R.id.submit_area).setBackgroundColor(submitAreaBackground)
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
                android.support.v7.appcompat.R.attr.colorPrimary, value, true)) {
            return null
        }
        val colorPrimary = value.data
        val defaultColor = baseColor.defaultColor
        return ColorStateList(arrayOf(DISABLED_STATE_SET, CHECKED_STATE_SET, View.EMPTY_STATE_SET), intArrayOf(baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor))
    }


    override fun dispatchKeyEventPreIme(event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_BACK ->
                searchViewController.doClose()
        }
        return super.dispatchKeyEventPreIme(event)
    }

    interface ISearchViewController {
        fun doClose()
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
        private val DISABLED_STATE_SET = intArrayOf(-android.R.attr.state_enabled)
    }
}
