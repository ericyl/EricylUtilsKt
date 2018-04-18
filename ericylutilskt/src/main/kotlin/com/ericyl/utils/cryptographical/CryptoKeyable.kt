package com.ericyl.utils.cryptographical

/**
 * @author ericyl
 * 2017/7/31
 */
internal interface CryptoKeyable<out T> {
    fun getKey(): T
}