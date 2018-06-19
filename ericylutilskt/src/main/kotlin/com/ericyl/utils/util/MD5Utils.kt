package com.ericyl.utils.util

import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File
import java.security.MessageDigest

/**
 * @author ericyl
 */
fun String.getStringMD5(): String {
    val md5 = MessageDigest.getInstance("MD5")
    return md5.digest(toByteArray()).byteHex()
}

fun File.getFileMD5(): String {
    val md5 = MessageDigest.getInstance("MD5")
    val data = readBytes()
    md5.update(data, 0, data.size)
    return md5.digest().byte2Hex()
}

private fun ByteArray.byteHex(): String {
    return StringBuilder().let { sb ->
        map { Integer.toHexString(it.toInt() and 0XFF) }.forEach {
            if (it.length == 1)
                sb.append("0$it")
            else
                sb.append(it)
        }
        sb
    }.toString().toUpperCase()
}

private fun ByteArray.byte2Hex(): String {
    return StringBuilder().let { sb ->
        map { Integer.toHexString(it.toInt() and 0XFF) }.forEachWithIndex { index, it ->
            if (it.length == 1)
                sb.append("0$it")
            else
                sb.append(it)
            if (index < size - 1)
                sb.append(":")
        }
        sb
    }.toString().toUpperCase()
}