package com.ericyl.utils.util

import java.io.File
import java.text.DecimalFormat

/**
 * @author ericyl on 2017/12/19.
 */

fun getFolderSize(file: File): Long {
    var size: Long = 0
    val fileList = file.listFiles()
    if (fileList == null || fileList.isEmpty())
        return 0
    for (i in fileList.indices) {
        size = if (fileList[i].isDirectory) {
            size + getFolderSize(fileList[i])

        } else {
            size + fileList[i].length()

        }
    }
    return size
}

fun deleteFiles(file: File) {
    if (!file.exists())
        return
    if (file.isFile)
        file.delete()
    else if (file.isDirectory) {
        val childFiles = file.listFiles()
        if (childFiles == null || childFiles.isEmpty()) {
            file.delete()
            return
        }
        for (childFile in childFiles) {
            deleteFiles(childFile)
        }
        file.delete()
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