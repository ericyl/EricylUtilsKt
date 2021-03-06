package com.ericyl.utils.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ericyl.utils.R

/**
 * @author ericyl
 */
fun Context.checkPermission(permission: String) = grantPermission(ContextCompat.checkSelfPermission(this, permission))

fun Context.checkPermissions(vararg permissions: String): Array<String>? {
    return permissions.partition { checkPermission(it) }.second.toTypedArray()
}

fun Activity.requestUsedPermissions(
    requestCode: Int,
    vararg permissions: String,
    action: ((permissions: Array<out String>) -> Unit)? = null
) {
    permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }.let {
        if (it && permissions.isNotEmpty() && action != null)
            action(permissions)
        else
            ActivityCompat.requestPermissions(this, permissions, requestCode)
    }
}

fun Fragment.requestUsedPermissions(
    requestCode: Int,
    vararg permissions: String,
    action: ((permissions: Array<out String>) -> Unit)? = null
) {
    permissions.any { shouldShowRequestPermissionRationale(it) }.let {
        if (it && permissions.isNotEmpty() && action != null)
            action(permissions)
        else
            requestPermissions(permissions, requestCode)
    }
}

fun grantPermission(status: Int) = status == PackageManager.PERMISSION_GRANTED
fun denyPermission(status: Int) = status == PackageManager.PERMISSION_DENIED

fun Context.permissionName(permission: String): String? {
    return when (permission) {
        Manifest.permission.READ_PHONE_STATE -> getString(R.string.phone)
        Manifest.permission.CALL_PHONE -> getString(R.string.phone)
        Manifest.permission.WRITE_EXTERNAL_STORAGE -> getString(R.string.external_storage)
        else -> null
    }
}

