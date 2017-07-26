package com.ericyl.utils.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.support.annotation.RequiresPermission
import android.telephony.TelephonyManager

/**
 * Created by ericyl on 2017/7/26.
 */
enum class IMSI(val tag: String) {
    CMCC("中国移动"), CHINA_UNICOM("中国联通"), CHINA_TELECOM("中国电信");

    companion object {
        fun getImsi(subscriberId: String): IMSI? {
            return when {
                subscriberId.startsWith("46002") || subscriberId.startsWith("46002") -> IMSI.CMCC
                subscriberId.startsWith("46001") -> IMSI.CHINA_UNICOM
                subscriberId.startsWith("46003") -> IMSI.CHINA_TELECOM
                else -> null
            }
        }
    }

    override fun toString(): String {
        return tag
    }

}


@RequiresPermission(value = Manifest.permission.READ_PHONE_STATE)
@SuppressLint("HardwareIds")
fun ContextWrapper.getImsi(): IMSI? =
        IMSI.getImsi((getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).subscriberId)