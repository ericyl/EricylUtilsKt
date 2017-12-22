package com.ericyl.utils.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

import com.ericyl.utils.R
import com.ericyl.utils.annotation.LoadStatus
import kotlinx.android.synthetic.main.layout_loading.view.*
import org.jetbrains.anko.dip

class LoadingLayout(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    @ColorInt
    private var failedImageColor: Int
    private var showProgressBar: Boolean
    private var showFailedImage: Boolean
    private var failedImage: Drawable
    private var loadingMessage: String
    private var failedMessage: String
    private var finishMessage: String

    private var onLoadingClickListener: View.OnClickListener? = null
    private var onFailedClickListener: View.OnClickListener? = null
    private var onFinishClickListener: View.OnClickListener? = null

    @LoadStatus
    @get:LoadStatus
    private var loadStatus: Int = LoadStatus.LOADING

    fun setFinishMessage(finishMessage: String) {
        this.finishMessage = finishMessage
    }

    fun setFailedMessage(failedMessage: String) {
        this.failedMessage = failedMessage
    }

    fun setLoadingMessage(loadingMessage: String) {
        this.loadingMessage = loadingMessage
    }

    fun setFailedImage(failedImage: Drawable) {
        this.failedImage = failedImage
    }

    fun setShowProgressBar(showProgressBar: Boolean) {
        this.showProgressBar = showProgressBar
        pbLoading.visibility = if (showProgressBar) View.VISIBLE else View.GONE
    }

    fun setShowFailedImage(showFailedImage: Boolean) {
        this.showFailedImage = showFailedImage
        imgError.visibility = if (showFailedImage) View.VISIBLE else View.GONE
    }

    fun setOnLoadingClickListener(onLoadingClickListener: View.OnClickListener) {
        this.onLoadingClickListener = onLoadingClickListener
    }

    fun setOnFailedClickListener(onFailedClickListener: View.OnClickListener) {
        this.onFailedClickListener = onFailedClickListener
    }

    fun setOnFinishClickListener(onFinishClickListener: View.OnClickListener) {
        this.onFinishClickListener = onFinishClickListener
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, defStyleAttr, R.style.defaultStyle_LoadingLayout)
        try {
            failedImageColor = a.getColor(R.styleable.LoadingLayout_failedImageColor, Color.TRANSPARENT)
            showProgressBar = a.getBoolean(R.styleable.LoadingLayout_showProgressBar, true)
            showFailedImage = a.getBoolean(R.styleable.LoadingLayout_showFailedImage, true)
            failedImage = a.getDrawable(R.styleable.LoadingLayout_failedImage)
            loadingMessage = a.getString(R.styleable.LoadingLayout_loadingMessage)
            failedMessage = a.getString(R.styleable.LoadingLayout_failedMessage)
            finishMessage = a.getString(R.styleable.LoadingLayout_finishMessage)
        } finally {
            a.recycle()
        }
        initView(context)
    }

    private fun initView(context: Context) {
        gravity = Gravity.CENTER
        val padding = dip(8.0f)
        setPadding(padding, padding, padding, padding)
        LayoutInflater.from(context).inflate(R.layout.layout_loading, this, true)
        pbLoading.visibility = View.GONE
        imgError.visibility = View.GONE
        tvMsg.visibility = View.GONE
        imgError.setImageDrawable(failedImage)
        imgError.setColorFilter(failedImageColor)
        setStatus(LoadStatus.LOADING)
    }

    fun setStatus(@LoadStatus status: Int) {
        when (status) {
            LoadStatus.LOADING -> showLoading()
            LoadStatus.FAILED -> showFailed()
            LoadStatus.FINISH -> showFinish()
        }
        loadStatus = status
    }

    private fun showLoading() {
        if (showProgressBar)
            pbLoading.visibility = View.VISIBLE
        imgError.visibility = View.GONE
        tvMsg.visibility = View.VISIBLE
        tvMsg.text = loadingMessage
        if (onLoadingClickListener != null)
            setOnClickListener(onLoadingClickListener)
        else
            isClickable = false

    }

    private fun showFailed() {
        if (showFailedImage)
            imgError.visibility = View.VISIBLE
        pbLoading.visibility = View.GONE
        tvMsg.visibility = View.VISIBLE
        tvMsg.text = failedMessage
        if (onFailedClickListener != null)
            setOnClickListener(onFailedClickListener)
        else
            isClickable = false

    }

    private fun showFinish() {
        imgError.visibility = View.GONE
        pbLoading.visibility = View.GONE
        tvMsg.visibility = View.VISIBLE
        tvMsg.text = finishMessage
        if (onFinishClickListener != null)
            setOnClickListener(onFinishClickListener)
        else
            isClickable = false
    }


}
