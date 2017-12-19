package com.ericyl.utils.util

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

import java.io.File
import java.util.ArrayList


fun Context.installApk(filePath: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW)
    var file: File? = null
    if (!filePath.isEmpty())
        file = File(filePath)
    return if (file != null && file.exists() && file.isFile) {
        intent.setDataAndType(Uri.parse("file://" + filePath),
                "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        true
    } else false
}

fun Context.uninstallApk(packageName: String) {
    val packageURI = Uri.parse("package:" + packageName)
    val uninstallIntent = Intent(Intent.ACTION_DELETE, packageURI)
    startActivity(uninstallIntent)
}

fun Context.findApk(packageName: String): Boolean {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            packageManager.getApplicationInfo(packageName,
                    PackageManager.MATCH_UNINSTALLED_PACKAGES)
        } else {
            packageManager.getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES)
        }
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return false
}

fun Context.getVersionName(packageName: String): String? {
    val packageInfoList = getAllPackageInfos()
    return packageInfoList
            .first { it.packageName == packageName }.versionName

}

fun Context.getVersionCode(packageName: String): Int {
    val packageInfoList = getAllPackageInfos()
    return packageInfoList
            .first { it.packageName == packageName }.versionCode

}

internal fun Context.getAllPackageInfos(): List<PackageInfo> {
    return packageManager.getInstalledPackages(0)
}

fun Context.getPackageInfos(isSystem: Boolean = false): List<PackageInfo> {
    val packageInfos = ArrayList<PackageInfo>()
    val allPackageInfos = getAllPackageInfos()
    for (packageInfo in allPackageInfos) {
        val appInfo = packageInfo.applicationInfo
        isSystem.doIsOrNotAction({
            if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM > 0)
                packageInfos.add(packageInfo)
        }, {
            if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0)
                packageInfos.add(packageInfo)
        })


    }
    return packageInfos

}

