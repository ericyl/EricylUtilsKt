package com.ericyl.utils.cryptographical

/**
 * Created by ericyl on 2017/7/31.
 */
interface Cryptographical {

    fun encrypt(byteArray: ByteArray): ByteArray

    fun decrypt(byteArray: ByteArray): ByteArray

}