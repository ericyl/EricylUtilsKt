package com.ericyl.utils.util

import android.util.Log

/**
 * @author ericyl
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

//It's a joke
inline fun <reified T> T.showLog(message: Any? = "Di lin Di lin. the message is $this", level: Int = Log.DEBUG): Int? {
    return ::log.getLog(T::class.java.simpleName, level)(message)
}

fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.getLog(p1: P1, p2: P2) = fun(p3: P3) = this(p1, p2, p3)

fun log(tag: String, level: Int, message: Any?): Int? {

    return when (level) {
        Log.DEBUG -> showLog(message, tag, Log::d, Log::d)
        Log.ERROR -> showLog(message, tag, Log::e, Log::e)
        Log.VERBOSE -> showLog(message, tag, Log::v, Log::v)
        Log.ASSERT -> showLog(message, tag, Log::wtf, Log::wtf)
        Log.INFO -> showLog(message, tag, Log::i, Log::i)
        Log.WARN -> showLog(message, tag, Log::w, Log::w)
        else -> null
    }
}

private fun showMessage(message: Any?): String {
    return when (message) {
        is String -> message
        is Throwable -> "${message.message}"
        else -> "$message"
    }
}

private fun showLog(message: Any?, tag: String, action1: (tag: String, message: String, tr: Throwable) -> Int?, action2: (tag: String, message: String) -> Int?): Int? {
    return if (message is Throwable) action1.invoke(tag, showMessage(message), message) else action2.invoke(tag, showMessage(message))
}