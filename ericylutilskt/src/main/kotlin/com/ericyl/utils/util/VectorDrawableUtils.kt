package com.ericyl.utils.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.graphics.drawable.VectorDrawableCompat

/**
 * Created by Vipul on 28/12/16.
 * update ericyl
 */
fun Context.getVectorDrawable(@DrawableRes drawableResId: Int): Drawable? {
    return VectorDrawableCompat.create(resources, drawableResId, theme)
}

fun Context.getVectorDrawable(@DrawableRes drawableResId: Int, @ColorInt color: Int): Drawable {
    val drawable = getVectorDrawable(drawableResId)
    drawable!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    return drawable
}

fun Context.getVectorBitmap(@DrawableRes drawableId: Int): Bitmap {
    val drawable = getVectorDrawable(drawableId)
    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
