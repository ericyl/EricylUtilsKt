package com.ericyl.utils.cryptographical

/**
 * Created by ericyl on 2017/7/31.
 */
internal interface CryptoKeyable<out T> {
    fun getKey(): T
}