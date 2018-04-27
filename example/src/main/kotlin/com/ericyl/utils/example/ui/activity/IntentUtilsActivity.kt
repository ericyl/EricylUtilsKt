package com.ericyl.utils.example.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.View
import com.ericyl.utils.example.BuildConfig
import com.ericyl.utils.example.R
import com.ericyl.utils.ui.activity.BaseActivity
import com.ericyl.utils.ui.widget.ActionBadgeLayout
import com.ericyl.utils.util.*
import kotlinx.android.synthetic.main.activity_intents_utils.*
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast
import java.io.File

private const val CODE_CALL_PHONE = 1

class IntentUtilsActivity : BaseActivity(), View.OnClickListener {

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_utils, menu)
        val menuItem = menu.findItem(R.id.action_extension)
        val actionBadgeLayout = menuItem.actionView as ActionBadgeLayout
        actionBadgeLayout.setText("1000")
        return super.onCreateOptionsMenu(menu)
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnChooseApp -> openFile("${BuildConfig.APPLICATION_ID}.fileProvider",
                    "${Environment.getExternalStorageDirectory().path}${File.separator}$fileName")
            R.id.btnOpenGallery -> openGallery(1)
            R.id.btnMakeCall -> {
                checkPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ?.doIsOrNotEmptyAction(
                                { callPhone() },
                                {
                                    requestUsedPermissions(
                                            CODE_CALL_PHONE,
                                            Manifest.permission.READ_PHONE_STATE,
                                            Manifest.permission.CALL_PHONE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            action = { e ->
                                                Snackbar.make(btnMakeCall, R.string.please_allow_to_open_permission, Snackbar.LENGTH_SHORT).setAction(R.string.allow) {
                                                    requestUsedPermissions(CODE_CALL_PHONE, *e)
                                                }.show()
                                            }
                                    )
                                }
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
    private fun callPhone() = getImsi().let {
        showLog(it.toString())
        when (it) {
            IMSI.CHINA_TELECOM -> makeCall("10000")
            IMSI.CHINA_UNICOM -> makeCall("10010")
            IMSI.CMCC -> makeCall("10086")
            else -> toast(R.string.can_not_get_imsi_info)
        }
    }


}
