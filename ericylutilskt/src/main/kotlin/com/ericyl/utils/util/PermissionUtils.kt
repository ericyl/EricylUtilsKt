package com.ericyl.utils.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.ericyl.utils.R

/**
 * @author ericyl
 */
internal fun Context.checkPermission(permission: String) = grantPermission(ContextCompat.checkSelfPermission(this, permission))

internal fun Context.checkPermissions(vararg permissions: String): Array<String>? {
    return permissions.partition { checkPermission(it) }.second.toTypedArray()
}

internal fun Activity.requestUsedPermissions(requestCode: Int, vararg permissions: String, action: ((permissions: Array<out String>) -> Unit)? = null) {
    permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }.let {
        if (it && permissions.isNotEmpty() && action != null)
            action(permissions)
        else
            ActivityCompat.requestPermissions(this, permissions, requestCode)
    }
}

internal fun Fragment.requestUsedPermissions(requestCode: Int, vararg permissions: String, action: ((permissions: Array<out String>) -> Unit)? = null) {
    permissions.any { shouldShowRequestPermissionRationale(it) }.let {
        if (it && permissions.isNotEmpty() && action != null)
            action(permissions)
        else
            requestPermissions(permissions, requestCode)
    }
}

internal fun grantPermission(status: Int) = status == PackageManager.PERMISSION_GRANTED
internal fun denyPermission(status: Int) = status == PackageManager.PERMISSION_DENIED

internal fun Context.permissionName(permission: String): String? {
    return when (permission) {
        Manifest.permission.READ_PHONE_STATE -> getString(R.string.phone)
        Manifest.permission.CALL_PHONE -> getString(R.string.phone)
        Manifest.permission.WRITE_EXTERNAL_STORAGE -> getString(R.string.external_storage)
        else -> null
    }
}

