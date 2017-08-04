package com.ericyl.utils.cryptographical.aes

import com.ericyl.utils.cryptographical.CryptoKeyable
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Created by ericyl on 2017/7/31.
 */
internal class AESCryptoKey(private val bytes: ByteArray) : CryptoKeyable<SecretKeySpec> {

    constructor(key: SecretKey) : this(key.encoded)

    override fun getKey(): SecretKeySpec {
        return SecretKeySpec(bytes, "AES")
    }


}


