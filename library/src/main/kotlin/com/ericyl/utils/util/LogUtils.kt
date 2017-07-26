package com.ericyl.utils.util

import android.util.Log

/**
 * Created by ericyl on 2017/7/26.
 */


inline fun <reified T> T.showDebugLog(message: Any?): Int? {
    return showLog(message, Log.DEBUG)
}

inline fun <reified T> T.showErrorLog(message: Any?): Int? {
    return showLog(message, Log.ERROR)
}

inline fun <reified T> T.showVerboseLog(message: Any?): Int? {
    return showLog(message, Log.VERBOSE)
}

inline fun <reified T> T.showAssertLog(message: Any?): Int? {
    return showLog(message, Log.ASSERT)
}

inline fun <reified T> T.showInfoLog(message: Any?): Int? {
    return showLog(message, Log.INFO)
}

inline fun <reified T> T.showWarnLog(message: Any?): Int? {
    return showLog(message, Log.WARN)
}

inline fun <reified T> T.showLog(message: Any?, level: Int? = Log.DEBUG): Int? {
    return ::log.getLog(T::class.java.simpleName, level!!)(message)
}

fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.getLog(p1: P1, p2: P2) = fun(p3: P3) = this(p1, p2, p3)

fun log(tag: String, level: Int, message: Any?): Int? {
    return when (message) {
        is String -> when (level) {
            Log.DEBUG -> Log.d(tag, message)
            Log.ERROR -> Log.e(tag, message)
            Log.VERBOSE -> Log.v(tag, message)
            Log.ASSERT -> Log.wtf(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.WARN -> Log.w(tag, message)
            else -> null
        }
        is Throwable -> when (level) {
            Log.DEBUG -> Log.d(tag, "${message.message}", message)
            Log.ERROR -> Log.e(tag, "${message.message}", message)
            Log.VERBOSE -> Log.v(tag, "${message.message}", message)
            Log.ASSERT -> Log.wtf(tag, "${message.message}", message)
            Log.INFO -> Log.i(tag, "${message.message}", message)
            Log.WARN -> Log.w(tag, "${message.message}", message)
            else -> null
        }
        else -> when (level) {
            Log.DEBUG -> Log.d(tag, "$message")
            Log.ERROR -> Log.e(tag, "$message")
            Log.VERBOSE -> Log.v(tag, "$message")
            Log.ASSERT -> Log.wtf(tag, "$message")
            Log.INFO -> Log.i(tag, "$message")
            Log.WARN -> Log.w(tag, "$message")
            else -> null
        }
    }
}