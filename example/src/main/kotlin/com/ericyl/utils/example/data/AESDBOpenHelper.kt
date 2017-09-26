package com.ericyl.utils.example.data

import android.content.Context
import com.ericyl.utils.example.utils.DATA_DATABASE_NAME
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper

/**
 * Created by ericyl on 2017/8/7.
 */

class AESDBOpenHelper(context: Context) : SQLiteOpenHelper(context, DATA_DATABASE_NAME, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null)
            createAESTable(p0)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    private fun createAESTable(p0: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $INDEX  INTEGER PRIMARY KEY AUTOINCREMENT, $NAME VARCHAR, $TYPE VARCHAR, $PWD VARCHAR, $ENTRY_ALIAS VARCHAR, $ENTRY_PWD VARCHAR )"
        p0.execSQL(sql)
    }

}