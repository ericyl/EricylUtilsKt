package com.ericyl.utils.util

import android.graphics.Bitmap
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.util.*

/**
 * @author ericyl
 */
val hints = fun(formats: List<BarcodeFormat>): MutableMap<DecodeHintType, Any> {
    val hints: MutableMap<DecodeHintType, Any> = EnumMap<DecodeHintType, Any>(DecodeHintType::class.java)
    hints[DecodeHintType.POSSIBLE_FORMATS] = formats
    return hints
}

fun MutableMap<DecodeHintType, Any>.addHints(localHints: Map<DecodeHintType, Any>): MutableMap<DecodeHintType, Any> {
    this.putAll(localHints)
    return this
}

fun MutableMap<DecodeHintType, Any>.addHints(hintType: DecodeHintType, hint: Any): MutableMap<DecodeHintType, Any> {
    this[hintType] = hint
    return this
}

private fun getMultiFormatReader(hints: Map<DecodeHintType, Any>): MultiFormatReader {
    val multiFormatReader = MultiFormatReader()
    multiFormatReader.setHints(hints)
    return multiFormatReader
}

fun getZXingDecodeResult(bitmap: Bitmap, hints: Map<DecodeHintType, Any> = hints(listOf(BarcodeFormat.AZTEC, BarcodeFormat.MAXICODE, BarcodeFormat.RSS_EXPANDED, BarcodeFormat.UPC_EAN_EXTENSION,
        BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.CODE_39, BarcodeFormat.CODE_93,
        BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR, BarcodeFormat.QR_CODE, BarcodeFormat.DATA_MATRIX, BarcodeFormat.PDF_417))): Result {
    val width = bitmap.width
    val height = bitmap.height
    val pixels = IntArray(width * height)
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
    val source = RGBLuminanceSource(width, height, pixels)
    return getMultiFormatReader(hints).decodeWithState(BinaryBitmap(HybridBinarizer(source)))
}