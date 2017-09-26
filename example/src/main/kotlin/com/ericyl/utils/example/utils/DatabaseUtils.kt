package com.ericyl.utils.example.utils

import android.content.ContextWrapper
import net.sqlcipher.database.SQLiteDatabase

private const val URI = "content://com.ericyl.example.provider.content/"
const val DATA_DATABASE_NAME = "data"

fun ContextWrapper.createDataDatabase(): SQLiteDatabase{

    SQLiteDatabase.loadLibs(this)
    val file = getDatabasePath("$DATA_DATABASE_NAME.db")
    if (!file.exists() && file.mkdirs()) {
        file.delete()
    }

    return SQLiteDatabase.openOrCreateDatabase(file, "test", null)
}