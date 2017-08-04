package com.ericyl.utils.cryptographical.aes

import com.ericyl.utils.cryptographical.Cryptographical
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by ericyl on 2017/7/31.
 */
internal class AESCryptoImpl private constructor(private val key: SecretKeySpec, algorithm: String, iv: ByteArray) : Cryptographical {

    private val ivSpec = IvParameterSpec(iv)
    private val cipher = Cipher.getInstance(algorithm)

    companion object newInstance {
        fun create(byteArray: ByteArray, iv: ByteArray, algorithm: String = "AES/CBC/PKCS7PADDING"): AESCryptoImpl {
            return AESCryptoImpl(AESCryptoKey(byteArray).getKey(), algorithm, iv)
        }
    }

    override fun encrypt(byteArray: ByteArray): ByteArray {
        return doFinal(Cipher.ENCRYPT_MODE, byteArray)
    }

    override fun decrypt(byteArray: ByteArray): ByteArray {
        return doFinal(Cipher.DECRYPT_MODE, byteArray)
    }

    private fun doFinal(opmode: Int, input: ByteArray): ByteArray {
        cipher.init(opmode, key, ivSpec)
        return cipher.doFinal(input)
    }


}