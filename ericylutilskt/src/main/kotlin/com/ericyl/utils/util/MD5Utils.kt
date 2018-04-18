package com.ericyl.utils.util

import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File
import java.security.MessageDigest

/**
 * @author ericyl
 */
fun getStringMD5(str: String): String {
    val md5 = MessageDigest.getInstance("MD5")
    val data = md5.digest(str.toByteArray())
    return byteHex(data)
}

fun getFileMD5(file: File): String {
    val md5 = MessageDigest.getInstance("MD5")
    val data = file.readBytes()
    md5.update(data, 0, data.size)
    return byte2Hex(md5.digest())
}

private fun byteHex(bytes: ByteArray): String {
    return StringBuilder().let { sb ->
        bytes.map { Integer.toHexString(it.toInt() and 0XFF) }.forEach {
            if (it.length == 1)
                sb.append("0$it")
            else
                sb.append(it)
        }
        sb
    }.toString().toUpperCase()
}

private fun byte2Hex(bytes: ByteArray): String {
    return StringBuilder().let { sb ->
        bytes.map { Integer.toHexString(it.toInt() and 0XFF) }.forEachWithIndex { index, it ->
            if (it.length == 1)
                sb.append("0$it")
            else
                sb.append(it)
            if (index < bytes.size - 1)
                sb.append(":")
        }
        sb
    }.toString().toUpperCase()
}