package com.ericyl.utils.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.content.FileProvider.getUriForFile

import java.io.File
import java.util.ArrayList


fun Context.installApk(filePath: String, authority: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW)
    var file: File? = null
    if (filePath.isNotEmpty())
        file = File(filePath)
    return if (file != null && file.exists() && file.isFile) {
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getUriForFile(this, authority, file)
        } else {
            Uri.fromFile(file)
        }

        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
        true
    } else false
}

fun Context.uninstallApk(packageName: String) {
    val packageURI = Uri.parse("package:$packageName")
    val uninstallIntent = Intent(Intent.ACTION_DELETE, packageURI)
    startActivity(uninstallIntent)
}

fun Context.findApk(packageName: String): Boolean {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.MATCH_UNINSTALLED_PACKAGES
            )
        } else {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_UNINSTALLED_PACKAGES
            )
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

fun Context.getVersionCode(packageName: String): Long {
    val packageInfoList = getAllPackageInfos()
    return packageInfoList.first { it.packageName == packageName }.longVersionCode
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

/**
 * Activity jump to other package activity
 *
 * @param packageName       other activity package name like{com.ericyl.demo}
 * @param startActivityName other activity name like{com.ericyl.demo.DemoActivity}
 * @param bundle            data bundle
 * @param action            intent.action link{Intent.ACTION_*}
 * @param flags             intent.flag link{Intent.ACTION_*}
 * default{Intent.FLAG_ACTIVITY_NEW_TASK}
 */
fun Context.jumpActivity(
    packageName: String,
    startActivityName: String,
    bundle: Bundle? = null,
    action: String? = null,
    flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK
) {
    val intent = Intent()
    if (action != null)
        intent.action = action
    intent.flags = flags
    intent.component = ComponentName(packageName, startActivityName)
    if (bundle != null)
        intent.putExtras(bundle)
    startActivity(intent)
}
