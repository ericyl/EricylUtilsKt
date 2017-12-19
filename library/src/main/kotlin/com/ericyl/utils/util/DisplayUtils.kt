package com.ericyl.utils.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import android.view.Surface

/**
 * @author ericyl
 */
//fun ContextWrapper.getDensity(): Float {
//    return resources.displayMetrics.density
//}

//private fun ContextWrapper.getScaledDensity(): Float {
//    return resources.displayMetrics.scaledDensity
//}

fun ContextWrapper.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun ContextWrapper.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

fun dp2px(context: Context, dp: Float, density: Float = context.resources.displayMetrics.density): Float {
    return dp * density + 0.5f * if (dp >= 0) 1 else -1
}

fun px2dp(context: Context, px: Float, density: Float = context.resources.displayMetrics.density): Float {
    return px / density + 0.5f * if (px >= 0) 1 else -1
}

fun sp2px(context: Context, sp: Float, scaledDensity: Float = context.resources.displayMetrics.density): Float {
    return sp * scaledDensity + 0.5f * if (sp >= 0) 1 else -1
}

fun px2sp(context: Context, px: Float, scaledDensity: Float = context.resources.displayMetrics.density): Float {
    return px / scaledDensity + 0.5f * if (px >= 0) 1 else -1
}

/**
 * 获取当前屏幕旋转角度
 *
 * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
 */
fun Activity.getScreenRotation(): Int {
    return when (windowManager.defaultDisplay.rotation) {
        Surface.ROTATION_90 -> 90
        Surface.ROTATION_180 -> 180
        Surface.ROTATION_270 -> 270
        else -> 0
    }
}


/**
 * 根据传入的角度设置当前屏幕的状态，使之不能旋转
 *
 * @param degree   旋转的角度。一般是通过[.getScreenRotation]方法获得的数据
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
fun Activity.lockActivity(degree: Int = getScreenRotation()) {
    requestedOrientation = when (degree) {
        90 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
        270 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        else -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}

/**
 * 判断系统设置是否打开了屏幕旋转
 *
 * @return is accelerometer rotation
 */
fun ContextWrapper.isAccelerometerRotation(): Boolean {
    return Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) != 0
}

/**
 * 判断是否横屏
 *
 * @return is landscape orientation
 */
fun ContextWrapper.isLandscapeOrientation(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}