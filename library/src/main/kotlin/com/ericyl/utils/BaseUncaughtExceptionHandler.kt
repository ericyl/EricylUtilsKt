package com.ericyl.utils

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Thread.UncaughtExceptionHandler
import java.util.Properties


import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Environment


class BaseUncaughtExceptionHandler(private val path: String, private val context: Context) : UncaughtExceptionHandler {
    private val _deviceCrashInfo = Properties()

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        ex.printStackTrace()
        context.collectCrashDeviceInfo()
        saveCrashInfoToFile(path, ex)
        val app = context
                .applicationContext as BaseApplication
        app.exitApp(-1)
    }


    private fun Context.collectCrashDeviceInfo() {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            if (packageInfo != null) {
                _deviceCrashInfo.put(VERSION_NAME, if (packageInfo.versionName == null) "not set" else packageInfo.versionName)
                _deviceCrashInfo.put(VERSION_CODE, packageInfo.versionCode.toString())
            }
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                _deviceCrashInfo.put(field.name, field.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun saveCrashInfoToFile(path: String, ex: Throwable): String? {
        val info = StringWriter()
        val printWriter = PrintWriter(info)
        ex.printStackTrace(printWriter)

        val result = info.toString()
        printWriter.close()
        _deviceCrashInfo.put(STACK_TRACE, result)

        try {
            val timestamp = System.currentTimeMillis()
            val fileName = "crash-$timestamp${CRASH_REPORTER_EXTENSION}"

            var file: File? = null
            if (Environment.getExternalStorageDirectory() != null) {
                file = File(path + File.separator
                        + fileName)
            }

            if (!file!!.exists()) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            val trace = FileOutputStream(file)
            _deviceCrashInfo.store(trace, "")
            trace.close()
            return fileName
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    companion object {
        private val VERSION_NAME = "VERSIONNAME"
        private val VERSION_CODE = "VERSIONCODE"
        private val STACK_TRACE = "STACK_TRACE"
        private val CRASH_REPORTER_EXTENSION = ".crs"
    }
}
