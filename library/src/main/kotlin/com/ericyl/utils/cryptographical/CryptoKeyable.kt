package com.ericyl.utils.cryptographical

/**
 * Created by ericyl on 2017/7/31.
 */
interface CryptoKeyable<T> {
    fun getKey(): T
}