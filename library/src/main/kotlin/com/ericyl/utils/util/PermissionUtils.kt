package com.ericyl.utils.util

import android.app.Activity
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by ericyl on 2017/7/24.
 */
fun ContextWrapper.checkPermission(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun ContextWrapper.checkPermissions(vararg permissions: String): Array<String> {
    return permissions.filter(String::isNotEmpty).map {
        if (!checkPermission(it))
            it
        else null
    }.filterNotNull().toTypedArray()
}

fun Activity.requestUsedPermissions(requestCode: Int, vararg permissions: String, showRationaleListener: IShowRationaleListener? = null) {
    for (permission in permissions.filter(String::isNotEmpty)) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showRationaleListener?.showRequestPermissionRationale()
            break
        }
    }
    if (showRationaleListener == null)
        ActivityCompat.requestPermissions(this, permissions, requestCode)

}

fun grantPermission(status: Int) = status == PackageManager.PERMISSION_GRANTED
fun denyPermission(status: Int) = status == PackageManager.PERMISSION_DENIED

interface IShowRationaleListener {
    fun showRequestPermissionRationale()
}
