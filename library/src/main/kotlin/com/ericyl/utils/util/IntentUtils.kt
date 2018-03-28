package com.ericyl.utils.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.webkit.MimeTypeMap
import com.ericyl.utils.R
import org.jetbrains.anko.toast
import java.io.File

/**
 * @author ericyl
 */

/**
 * @param mimeType          media type
 * @param providerName      like{ "${BuildConfig.APPLICATION_ID}.fileProvider" }
 * @param filePath          file local path
 * @param isChoose          createChooser?
 * @return open successful or failed
 */
@JvmOverloads
fun Context.openFile(providerName: String, filePath: String, mimeType: String? = null, isChoose: Boolean = true) {
    try {
        val intent = Intent().setAction(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        intent.data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            FileProvider.getUriForFile(this, providerName, File(filePath))
        } else Uri.fromFile(File(filePath))

        intent.type = mimeType ?: MimeTypeMap.getSingleton().getMimeTypeFromExtension(filePath.split(".").last())

        if (isChoose)
            startActivity(Intent.createChooser(intent, getString(R.string.choose_app_title)))
        else
            startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        toast(R.string.can_not_found_app)
    } catch (e: Exception) {
        toast(R.string.open_file_failed)
    }

}

fun Activity.openGallery(requestCode: Int) {
    val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE)
    startActivityForResult(Intent.createChooser(intent, getString(R.string.choose_app_title)), requestCode)
}