package com.ericyl.utils.util

import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat

/**
 * @author ericyl on 2017/12/19.
 */

fun File.getFolderSize(): Long {
    var size: Long = 0
    val fileList = listFiles()
    if (fileList == null || fileList.isEmpty())
        return 0
    for (i in fileList.indices) {
        size = if (fileList[i].isDirectory) {
            size + fileList[i].getFolderSize()

        } else {
            size + fileList[i].length()

        }
    }
    return size
}

fun File.deleteFiles() {
    if (!exists())
        return
    if (isFile)
        delete()
    else if (isDirectory) {
        val childFiles = listFiles()
        if (childFiles == null || childFiles.isEmpty()) {
            delete()
            return
        }
        for (childFile in childFiles) {
            childFile.deleteFiles()
        }
        delete()
    }
}

private val DOUBLE_DECIMAL_FORMAT = DecimalFormat(
        "0.##")
private val GB_2_BYTE = Math.pow(1024.0, 3.0)
private val MB_2_BYTE = Math.pow(1024.0, 2.0)
private val KB_2_BYTE = 1024.0

fun Long.formatFolderSize(): CharSequence {
    if (this <= 0)
        return "0 B"
    return when {
        this >= GB_2_BYTE -> "${DOUBLE_DECIMAL_FORMAT.format(this / GB_2_BYTE)} GB"
        this >= MB_2_BYTE -> "${DOUBLE_DECIMAL_FORMAT.format(this / MB_2_BYTE)} MB"
        this >= KB_2_BYTE -> "${DOUBLE_DECIMAL_FORMAT.format(this / KB_2_BYTE)} KB"
        else -> "$this B"
    }
}

fun ByteArray.saveToFile(path: String, flag: Boolean = false) {
    FileOutputStream(path, flag).use {
        it.write(this)
    }
}