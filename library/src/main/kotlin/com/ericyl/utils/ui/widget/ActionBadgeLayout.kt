package com.ericyl.utils.ui.widget

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.ericyl.utils.R
import org.jetbrains.anko.backgroundDrawable


class ActionBadgeLayout(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    @DrawableRes
    private var icon: Int
    @ColorRes
    private var badgeColor: Int

    private lateinit var imgIcon: ImageView
    private lateinit var tvBadge: TextView

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ActionBadgeLayout, 0, R.style.defaultStyle_ActionBadgeLayout)
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
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(context, badgeColor))
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
