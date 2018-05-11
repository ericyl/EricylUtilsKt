package com.ericyl.utils.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat

/**
 * Created by Vipul on 28/12/16.
 * update ericyl
 */
fun Context.getVectorDrawable(drawableResId: Int): Drawable? {
    return VectorDrawableCompat.create(resources, drawableResId, theme)
}

fun Context.getVectorDrawable(drawableResId: Int, colorFilter: Int): Drawable {
    val drawable = getVectorDrawable(drawableResId)
    drawable!!.setColorFilter(ContextCompat.getColor(this, colorFilter), PorterDuff.Mode.SRC_IN)
    return drawable
}

fun Context.getVectorBitmap(drawableId: Int): Bitmap {
    val drawable = getVectorDrawable(drawableId)
    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
