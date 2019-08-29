package com.ericyl.utils.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

import com.ericyl.utils.R
import com.ericyl.utils.util.randomColor

class CircleTextView(context: Context, attrs: AttributeSet, defStyleAttr: Int) : AppCompatTextView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    @ColorInt
    private var backgroundColor: Int
    private var isSingleText: Boolean

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView, defStyleAttr, R.style.defaultStyle_CircleTextView)
        try {
            backgroundColor = a.getColor(R.styleable.CircleTextView_backgroundColor, Color.TRANSPARENT)
            isSingleText = a.getBoolean(R.styleable.CircleTextView_singleText, false)
            if (isSingleText && !TextUtils.isEmpty(text))
                text = text.subSequence(0, 1).toString().toUpperCase()
        } finally {
            a.recycle()
        }
    }

    fun setSingleText(text: String) {
        if (isSingleText && !TextUtils.isEmpty(text))
            setText(text.subSequence(0, 1).toString().toUpperCase())
    }

    fun setRandomBackgroundColor() {
        setBackgroundColor(randomColor())
    }


    fun setBackgroundColorRes(context: Context, @ColorRes colorRes: Int) {
        setBackgroundColor(ContextCompat.getColor(context, colorRes))
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        this.backgroundColor = color
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        val radius = (Math.min(height, width) / 2).toFloat()

        paint.color = backgroundColor
        paint.isAntiAlias = true
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        super.onDraw(canvas)
    }


}