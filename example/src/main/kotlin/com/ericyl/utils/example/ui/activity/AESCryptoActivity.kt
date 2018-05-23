package com.ericyl.utils.example.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.View
import com.ericyl.utils.example.R
import com.ericyl.utils.example.R.id.*
import com.ericyl.utils.example.data.*
import com.ericyl.utils.example.utils.DATA_DATABASE_NAME
import com.ericyl.utils.ui.activity.BaseActivity
import com.ericyl.utils.util.*
import io.reactivex.*
import io.reactivex.Single.create
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_aescrypto.*
import net.sqlcipher.database.SQLiteDatabase
import org.jetbrains.anko.toast
import java.io.File
import java.security.KeyStore
import javax.crypto.SecretKey

class AESCryptoActivity : BaseActivity(false), View.OnClickListener {

    private lateinit var secretKey: SecretKey

    private val iv: ByteArray = getRandomBytes(16)//Base64.decode("$$$$", Base64.NO_WRAP)
    private var salt: ByteArray = getRandomBytes(32)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aescrypto)
    }


    override fun init(savedInstanceState: Bundle?) {
        btnCreateKey.setOnClickListener(this)
        btnReadKey.setOnClickListener(this)
        btnEncrypt.setOnClickListener(this)
        btnDecrypt.setOnClickListener(this)
        btnSaveKey.setOnClickListener(this)
        btnSaveKeyStore.setOnClickListener(this)
        btnBase64.setOnClickListener(this)
    }

    @SuppressLint("MissingPermission")
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnCreateKey -> {
                showLog(Base64.encodeToString(salt, Base64.NO_WRAP))
                secretKey = getSecretKey("example", salt)
            }

            R.id.btnReadKey -> {
                secretKey = readSecretKey(resources.assets.open("example.keystore"), "example", "example", "example")
            }

            R.id.btnEncrypt -> {
                val source = etSource.text.toString()
                if (TextUtils.isEmpty(source)) {
                    Snackbar.make(etSource, R.string.source_text_is_null, Snackbar.LENGTH_SHORT).show()
                    return
                }
                val enc = encrypt(secretKey, iv, source)
                tvCipher.text = enc
                showLog(enc)

            }

            R.id.btnDecrypt -> {
                val enc = tvCipher.text.toString()
                if (TextUtils.isEmpty(enc)) {
                    Snackbar.make(tvCipher, R.string.cipher_text_is_null, Snackbar.LENGTH_SHORT).show()
                    return
                }
                val dec = decrypt(secretKey, iv, enc)
                tvOriginal.text = dec
                showLog(dec)
            }


            R.id.btnSaveKey -> {
                saveKey(secretKey.encoded, "example.key")
            }

            R.id.btnSaveKeyStore ->
                create(SingleOnSubscribe<AESData> { e ->
                    SQLiteDatabase.loadLibs(this)
//                    try {
//                        val database = SQLiteDatabase.openDatabase(this.getDatabasePath("$DATA_DATABASE_NAME.db").absolutePath, "test", null, SQLiteDatabase.OPEN_READONLY)
//                        val cursor = database.query(TABLE_NAME, null, "$NAME = ? ", arrayOf("example.keystore"), null, null, null)
//                        database.close()
//                        cursor.use {
//                            if (it != null && it.moveToFirst()) {
//                                val name = it.getString(it.getColumnIndex(NAME))
//                                val type = it.getString(it.getColumnIndex(TYPE))
//                                val pwd = it.getString(it.getColumnIndex(PWD))
//                                val entryAlias = it.getString(it.getColumnIndex(ENTRY_ALIAS))
//                                val entryPwd = it.getString(it.getColumnIndex(ENTRY_PWD))
//                                e.onSuccess(AESData(name, type, pwd, entryAlias, entryPwd))
//                            } else
                    e.onSuccess(AESData("example.keystore", KeyStore.getDefaultType(), "example", "example", "example"))
//                        }
//                    } catch (ex: Exception) {
//                        e.onError(ex)
//                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
                    saveToKeyStore(secretKey, it.name, it.pwd, it.entryAlias, it.entryPwd, it.type)
                }.subscribe({
                    toast("success")
                }, {
                    it.printStackTrace()
                    toast(it.message ?: "error")
                })

            R.id.btnBase64 -> {
                showLog(Base64.encodeToString(secretKey.encoded, Base64.NO_WRAP))
            }
        }
    }


}
