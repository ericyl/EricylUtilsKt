package com.ericyl.utils.ui.widget.support.design

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class ScrollAwareBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {
    private val INTERPOLATOR = FastOutSlowInInterpolator()
    private var isAnimate = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (dyConsumed > 0 && !isAnimate && child.visibility == View.VISIBLE) {
            animateOut(child)
        } else if (dyConsumed < 0 && !isAnimate && child.visibility != View.VISIBLE) {
            animateIn(child)
        }
    }

    private fun animateOut(view: View) {
        ViewCompat.animate(view).translationY((view.height + getMarginBottom(view)).toFloat()).setInterpolator(INTERPOLATOR).withLayer()
                .setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationStart(view: View) {
                        this@ScrollAwareBehavior.isAnimate = true
                    }

                    override fun onAnimationCancel(view: View) {
                        this@ScrollAwareBehavior.isAnimate = false
                        animateIn(view)
                    }

                    override fun onAnimationEnd(view: View) {
                        this@ScrollAwareBehavior.isAnimate = false
                        view.visibility = View.INVISIBLE
                    }
                }).start()
    }

    private fun animateIn(view: View) {
        ViewCompat.animate(view).translationY(0f)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationEnd(view: View) {
                        this@ScrollAwareBehavior.isAnimate = false
                    }

                    override fun onAnimationCancel(view: View) {
                        this@ScrollAwareBehavior.isAnimate = false
                    }

                    override fun onAnimationStart(view: View) {
                        view.visibility = View.VISIBLE
                        this@ScrollAwareBehavior.isAnimate = true
                    }


                }).start()
    }

    private fun getMarginBottom(view: View): Int {
        val layoutParams = view.layoutParams
        return if (layoutParams is ViewGroup.MarginLayoutParams) {
            layoutParams.bottomMargin
        } else 0
    }

}