package com.ericyl.utils.util

import java.io.File

/**
 * @author ericyl on 2017/12/19.
 */
fun deleteFiles(file: File) {
    if (file.exists()) {
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
}