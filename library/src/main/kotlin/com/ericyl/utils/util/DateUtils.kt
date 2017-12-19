package com.ericyl.utils.util

import android.annotation.SuppressLint
import android.content.Context
import com.ericyl.utils.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author ericyl
 */
const val DATE_YMD_OTHER = "yyyyMMdd"
const val DATE_DEFAULT = "yyyy-MM-dd"
const val DATE_YMD = "yyyy/MM/dd"
const val DATE_MDY = "MM/dd/yyyy"

const val TIME_DEFAULT_12 = "hh:mm:ss"
const val TIME_DEFAULT_24 = "HH:mm:ss"

const val DATETIME_YMD_OTHER = "yyyyMMddHHmmss"
const val DATETIME_DEFAULT_12 = "yyyy-MM-dd hh:mm:ss a"
const val DATETIME_DEFAULT_24 = "yyyy-MM-dd HH:mm:ss"
const val DATETIME_YMD_12 = "yyyy/MM/dd hh:mm:ss a"
const val DATETIME_YMD_24 = "yyyy/MM/dd HH:mm:ss"
const val DATETIME_MDY_12 = "MM/dd/yyyy hh:mm:ss a"
const val DATETIME_MDY_24 = "MM/dd/yyyy HH:mm:ss"

const val DATETIME_ALL_OTHER = "yyyyMMddHHmmssSSS"
const val DATETIME_ALL = "yyyy-MM-dd HH:mm:ss.SSSZ E"

const val DAY_MILLIS = 24 * 60 * 60 * 1000
const val HOUR_MILLIS = 60 * 60 * 1000
const val MINUTE_MILLIS = 60 * 1000
const val SECOND_MILLIS = 1000
/**
 * G 年代标志符
 * y 年
 * M 月
 * d 日
 * h 时 在上午或下午 (1~12)
 * H 时 在一天中 (0~23)
 * m 分
 * s 秒
 * S 毫秒
 * E 星期
 * D 一年中的第几天
 * F 一月中第几个星期几
 * w 一年中第几个星期
 * W 一月中第几个星期
 * a 上午 / 下午 标记符
 * k 时 在一天中 (1~24)
 * K 时 在上午或下午 (0~11)
 * Z 时区
 */

fun getLastMinute(context: Context, time: Long): String {
    var localTime = time
    var days = 0
    if (localTime > DAY_MILLIS) {
        days = (time / DAY_MILLIS).toInt()
        localTime -= days * DAY_MILLIS
    }
    var hours = 0
    if (localTime > HOUR_MILLIS) {
        hours = (time / HOUR_MILLIS).toInt()
        localTime -= hours * HOUR_MILLIS
    }
    var minutes = 0
    if (localTime > MINUTE_MILLIS) {
        minutes = (time / MINUTE_MILLIS).toInt()
        localTime -= minutes * MINUTE_MILLIS
    }
    if (localTime > SECOND_MILLIS) {
        minutes += 1
    }
    return when {
        days > 0 -> String.format(context.getString(R.string.last_day_hour_minute), days, hours, minutes)
        hours > 0 -> String.format(context.getString(R.string.last_hour_minute), hours, minutes)
        else -> String.format(context.getString(R.string.last_minute), minutes)
    }
}

