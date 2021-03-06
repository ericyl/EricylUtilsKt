package com.ericyl.utils.ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

import com.ericyl.utils.R
import org.jetbrains.anko.backgroundDrawable


class ActionBadgeLayout(context: Context, attrs: AttributeSet, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    @DrawableRes
    private var icon: Int
    @ColorRes
    private var badgeColor: Int

    private lateinit var imgIcon: ImageView
    private lateinit var tvBadge: TextView

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ActionBadgeLayout, defStyleAttr, R.style.defaultStyle_ActionBadgeLayout)
        try {
            icon = a.getResourceId(R.styleable.ActionBadgeLayout_icon, -1)
            badgeColor = a.getResourceId(R.styleable.ActionBadgeLayout_badgeColor, R.attr.colorPrimary)
        } finally {
            a.recycle()
        }
        initView(context)
    }


    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_action_badge, this)
        imgIcon = findViewById(R.id.img_icon)
        tvBadge = findViewById(R.id.tv_badge)
        tvBadge.visibility = View.GONE
        if (icon != -1)
            imgIcon.setImageResource(icon)
        val drawable = ContextCompat.getDrawable(context, R.drawable.drawable_tv_badge_rectangle)
        DrawableCompat.setTintList(drawable!!, ContextCompat.getColorStateList(context, badgeColor))
        tvBadge.backgroundDrawable = drawable
    }

    fun setText(badge: String) {
        if (TextUtils.isEmpty(badge)) {
            tvBadge.visibility = View.GONE
            return
        }
        tvBadge.visibility = View.VISIBLE
        val value = badge.toIntOrNull()
        if (value != null && value > 99) {
            tvBadge.text = context.getString(R.string.more_than_99)
            return
        }
        tvBadge.text = badge
    }

}
