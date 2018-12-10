package com.ericyl.utils.cryptographical.rsa


import com.ericyl.utils.cryptographical.CryptoKeyable

import java.math.BigInteger
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


class RSACryptoKey<T> private constructor(private val data: T) : CryptoKeyable<RSAKeySpec> where T : RSAPrivateKey, T : RSAPublicKey {


    override fun getKey(): RSAKeySpec {
        return object : RSAKeySpec() {
            override fun getModulus(): BigInteger {
                return data.getModulus()
            }

            override fun getAlgorithm(): String {
                return data.getAlgorithm()
            }

            override fun getFormat(): String {
                return data.getFormat()
            }

            override fun getEncoded(): ByteArray {
                return data.getEncoded()
            }
        }
    }

    companion object {
        fun <T> getInstance(t: T): RSACryptoKey<*> where T : RSAPrivateKey, T : RSAPublicKey {
            return RSACryptoKey(t)
        }
    }
}
