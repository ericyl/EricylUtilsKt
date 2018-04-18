package com.ericyl.utils.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresPermission
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.UnsupportedEncodingException
import java.util.*

/**
 * @author ericyl
 */
enum class IMSI(private val tag: String) {
    CMCC("中国移动"), CHINA_UNICOM("中国联通"), CHINA_TELECOM("中国电信"), UNKNOWN("unknown");

    companion object {
        fun getImsi(subscriberId: String): IMSI {
            return when {
                subscriberId.startsWith("46002") || subscriberId.startsWith("46002") -> IMSI.CMCC
                subscriberId.startsWith("46001") -> IMSI.CHINA_UNICOM
                subscriberId.startsWith("46003") -> IMSI.CHINA_TELECOM
                else -> UNKNOWN
            }
        }
    }

    override fun toString(): String {
        return tag
    }

}

@RequiresPermission(value = Manifest.permission.READ_PHONE_STATE)
@SuppressLint("HardwareIds")
fun ContextWrapper.getImsi(): IMSI =
        IMSI.getImsi(getSimCode())

@RequiresPermission(value = Manifest.permission.READ_PHONE_STATE)
@SuppressLint("HardwareIds")
fun ContextWrapper.getSimCode(): String {
    return (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).subscriberId
}

@RequiresPermission(value = Manifest.permission.READ_PHONE_STATE)
@SuppressLint("HardwareIds")
fun Context.getDeviceUUID(): UUID? {
    var uuid: UUID? = null
    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    var deviceId =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                telephonyManager.deviceId
            else
                telephonyManager.imei

    if (TextUtils.isEmpty(deviceId)) {
        val macStream = try {
            FileReader("/sys/class/net/wlan0/address")
        } catch (e: FileNotFoundException) {
            try {
                FileReader("/sys/class/net/eth0/address")
            } catch (e1: FileNotFoundException) {
                null
            }
        }
        deviceId = macStream?.readText()
    }

    if (!TextUtils.isEmpty(deviceId)) {
        deviceId = android.provider.Settings.Secure.getString(contentResolver, android.provider.Settings.Secure.ANDROID_ID)
    }

    if (!TextUtils.isEmpty(deviceId))
        uuid = UUID.nameUUIDFromBytes(deviceId.toByteArray(charset("utf8")))

    return uuid
}


/**
 * @return windows, linux, mac os x...
 */
fun getOS(): String {
    return System.getProperty("os.name").toLowerCase()
}


enum class PhoneManufacturer {
    DEFAULT, MEI_ZU, LGE, HUA_WEI;

    companion object {
        fun getPhoneManufacturer(): PhoneManufacturer? {
            return when (android.os.Build.MANUFACTURER.toUpperCase()) {
                "MEIZU" -> MEI_ZU
                "LGE" -> LGE
                "HUAWEI" -> HUA_WEI
                else -> DEFAULT
            }
        }
    }

}

@SuppressLint("PrivateApi")
fun ContextWrapper.checkHasNavigationBar(): Boolean {
    return when (PhoneManufacturer.getPhoneManufacturer()) {
        PhoneManufacturer.MEI_ZU -> checkMeiZuHasNavigationBar()
        else -> {
            val showNavigationBarId = resources.getIdentifier("config_showNavigationBar", "bool", "android")
            var flag = false
            if (showNavigationBarId > 0)
                flag = resources.getBoolean(showNavigationBarId)
            if (!flag) {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val method = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = method.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    false
                } else "0" == navBarOverride
            } else flag
        }

    }
}

private fun ContextWrapper.checkMeiZuHasNavigationBar(): Boolean {
    return Settings.System.getInt(contentResolver, "mz_smartbar_auto_hide", 0) == 1
}

fun ContextWrapper.getNavigationBarHeight(): Int {
    return when (PhoneManufacturer.getPhoneManufacturer()) {
        PhoneManufacturer.MEI_ZU -> getMeiZuNavigationBarHeight()
        else -> {
            val navigationBarHeightId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (navigationBarHeightId > 0 && checkHasNavigationBar())
                resources.getDimensionPixelSize(navigationBarHeightId)
            else 0
        }
    }
}

@SuppressLint("PrivateApi")
private fun ContextWrapper.getMeiZuNavigationBarHeight(): Int {
    return if (checkMeiZuHasNavigationBar()) {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("mz_action_button_min_height")
            val height = Integer.parseInt(field.get(obj).toString())
            resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            getNavigationBarHeight()
        }

    } else 0
}

fun ContextWrapper.getStatusBarHeight(): Int {
    val statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (statusBarHeightId > 0)
        resources.getDimensionPixelSize(statusBarHeightId)
    else 0
}