package com.ericyl.utils.util

import android.Manifest
import android.app.Activity
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.ericyl.utils.R

/**
 * @author ericyl
 */
fun ContextWrapper.checkPermission(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun ContextWrapper.checkPermissions(vararg permissions: String): Array<String>? {
    return permissions.partition { checkPermission(it) }.second.toTypedArray()
}

fun Activity.requestUsedPermissions(requestCode: Int, vararg permissions: String, action: (permissions: List<String>) -> Unit) {
    permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }.let {
        if (it && permissions.isNotEmpty()) action(permissions.asList())
        else ActivityCompat.requestPermissions(this, permissions, requestCode)
    }
}

fun grantPermission(status: Int) = status == PackageManager.PERMISSION_GRANTED
fun denyPermission(status: Int) = status == PackageManager.PERMISSION_DENIED

fun ContextWrapper.permissionName(permission: String): String? {
    return when (permission) {
        Manifest.permission.READ_PHONE_STATE -> getString(R.string.phone)
        Manifest.permission.CALL_PHONE -> getString(R.string.phone)
        Manifest.permission.WRITE_EXTERNAL_STORAGE -> getString(R.string.external_storage)
        else -> null
    }
}

