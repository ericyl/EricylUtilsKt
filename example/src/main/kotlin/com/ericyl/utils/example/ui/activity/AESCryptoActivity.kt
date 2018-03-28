package com.ericyl.utils.example.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.view.View
import com.ericyl.utils.example.R
import com.ericyl.utils.example.data.*
import com.ericyl.utils.example.utils.DATA_DATABASE_NAME
import com.ericyl.utils.ui.activity.BaseActivity
import com.ericyl.utils.util.getDeviceUUID
import com.ericyl.utils.util.getRandomBytes
import com.ericyl.utils.util.saveToKeyStore
import com.ericyl.utils.util.showLog
import io.reactivex.*
import io.reactivex.Single.create
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_aescrypto.*
import net.sqlcipher.database.SQLiteDatabase
import org.jetbrains.anko.toast
import javax.crypto.SecretKey

class AESCryptoActivity : BaseActivity(false) {

    private lateinit var secretKey: SecretKey

    private val iv: ByteArray = Base64.decode("e+YXMeMgVaQLjtQt4AlmkQ==", Base64.NO_WRAP)//getRandomBytes(16)
    private val salt: ByteArray = getRandomBytes(32)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aescrypto)
    }


    override fun init(savedInstanceState: Bundle?) {
        btnCreateKey.setOnClickListener(this)
        btnSaveKey.setOnClickListener(this)
    }

    @SuppressLint("MissingPermission")
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnCreateKey ->
                showLog(getDeviceUUID())
            R.id.btnSaveKey ->
                create(SingleOnSubscribe<AESData> { e ->
                    SQLiteDatabase.loadLibs(this)
                    try {
                        val database = SQLiteDatabase.openDatabase(this.getDatabasePath("$DATA_DATABASE_NAME.db").absolutePath, "test", null, SQLiteDatabase.OPEN_READONLY)
                        val cursor = database.query(TABLE_NAME, null, "$NAME = ? ", arrayOf("example.keystore"), null, null, null)
                        database.close()
                        cursor.use {
                            if (it != null && it.moveToFirst()) {
                                val name = it.getString(it.getColumnIndex(NAME))
                                val type = it.getString(it.getColumnIndex(TYPE))
                                val pwd = it.getString(it.getColumnIndex(PWD))
                                val entryAlias = it.getString(it.getColumnIndex(ENTRY_ALIAS))
                                val entryPwd = it.getString(it.getColumnIndex(ENTRY_PWD))
                                e.onSuccess(AESData(name, type, pwd, entryAlias, entryPwd))
                            } else
                                e.onSuccess(AESData("example.keystore", "BKS", "example", "example", "example"))
                        }
                    } catch (ex: Exception) {
                        e.onError(ex)
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
                    saveToKeyStore(secretKey, it.name, it.pwd, it.entryAlias, it.entryPwd, it.type)
                }.subscribe({
                    toast("success")
                }, {
                    it.printStackTrace()
                    toast(it.message ?: "error")
                })
        }
    }


}
