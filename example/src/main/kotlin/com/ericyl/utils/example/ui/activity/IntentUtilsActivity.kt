package com.ericyl.utils.example.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.view.View
import com.ericyl.utils.example.BuildConfig
import com.ericyl.utils.example.R
import com.ericyl.utils.ui.BaseActivity
import com.ericyl.utils.util.*
import kotlinx.android.synthetic.main.activity_intents_utils.*
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast
import java.io.File

private const val CODE_CALL_PHONE = 1
class IntentUtilsActivity : BaseActivity() {

    private lateinit var fileName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intents_utils)
        fileName = intent.getStringExtra("FILE_NAME") ?: "IMG_1.jpg"

    }

    override fun init(savedInstanceState: Bundle?) {
        btnChooseApp.setOnClickListener(this)
        btnOpenGallery.setOnClickListener(this)
        btnMakeCall.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnChooseApp -> openFile("${BuildConfig.APPLICATION_ID}.fileProvider",
                    "${Environment.getExternalStorageDirectory().path}${File.separator}$fileName")
            R.id.btnOpenGallery -> openGallery(1)
            R.id.btnMakeCall -> {
                checkPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .doIsOrNotEmptyAction(
                                {
                                    requestUsedPermissions(CODE_CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, action = object : IShowRationaleListener {
                                        override fun showRequestPermissionRationale(permissions: Array<out String>) {
                                            Snackbar.make(btnMakeCall, R.string.please_allow_to_open_permission, Snackbar.LENGTH_SHORT).setAction(R.string.allow) {
                                                requestUsedPermissions(CODE_CALL_PHONE, *permissions)
                                            }.show()
                                        }
                                    })
                                },
                                { callPhone() }
                        )
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CODE_CALL_PHONE -> {
                grantResults.mapIndexed { index, value ->
                    if (denyPermission(value))
                        index
                    else null
                }.filterNotNull().map(permissions::get).toTypedArray().let { permissionArray ->
                    if (permissionArray.isNotEmpty())
                        permissionArray.map { permissionName(it) }.toSet().joinToString().let {
                            toast(getString(R.string.please_open_permission, it))
                        }
                    else callPhone()
                }

            }

        }
    }

    @SuppressLint("MissingPermission")
    fun callPhone() = getImsi().let {
        showLog(it.toString())
        when (it) {
            IMSI.CHINA_TELECOM -> makeCall(getString(R.string.china_telecom_service_phone_number))
            IMSI.CHINA_UNICOM -> makeCall(getString(R.string.china_unicom_service_phone_number))
            IMSI.CMCC -> makeCall(getString(R.string.cmcc_service_phone_number))
            else -> toast(R.string.can_not_get_imsi_info)
        }
    }


}
