package com.ericyl.utils.cryptographical.rsa


import com.ericyl.utils.cryptographical.Cryptographical
import com.ericyl.utils.cryptographical.exception.CryptoException

import java.io.ByteArrayOutputStream
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

import javax.crypto.Cipher

class RSACryptoImpl private constructor(private val key: RSAKeySpec, algorithm: String) : Cryptographical {

    private val cipher = Cipher.getInstance(algorithm)

    override fun encrypt(byteArray: ByteArray): ByteArray {
        try {
            return doFinal(Cipher.ENCRYPT_MODE, byteArray)
        } catch (e: Exception) {
            throw CryptoException(e)
        }

    }

    override fun decrypt(byteArray: ByteArray): ByteArray {
        try {
            return doFinal(Cipher.DECRYPT_MODE, byteArray)
        } catch (e: Exception) {
            throw CryptoException(e)
        }

    }

    @Throws(Exception::class)
    private fun doFinal(opmode: Int, input: ByteArray): ByteArray {
        cipher.init(opmode, key)
        return rsaSplitCodec(cipher, opmode, input, key.modulus.bitLength())
    }

    companion object {
        fun create(keySpec: RSAKeySpec, algorithm: String): RSACryptoImpl {
            try {
                return RSACryptoImpl(keySpec, algorithm)
            } catch (e: Exception) {
                throw CryptoException(e)
            }


        }

        fun <T> create(key: T, algorithm: String): RSACryptoImpl where T : RSAPrivateKey, T : RSAPublicKey {
            try {
                return RSACryptoImpl(RSACryptoKey.getInstance(key).getKey(), algorithm)
            } catch (e: Exception) {
                throw CryptoException(e)
            }
        }

        private fun rsaSplitCodec(cipher: Cipher, opmode: Int, datas: ByteArray, keySize: Int): ByteArray {
            val maxBlock: Int = if (opmode == Cipher.DECRYPT_MODE) {
                keySize / 8
            } else {
                keySize / 8 - 11
            }
            val out = ByteArrayOutputStream()
            out.use {
                var offSet = 0
                var buff: ByteArray
                var i = 0
                try {
                    while (datas.size > offSet) {
                        buff = if (datas.size - offSet > maxBlock) {
                            cipher.doFinal(datas, offSet, maxBlock)
                        } else {
                            cipher.doFinal(datas, offSet, datas.size - offSet)
                        }
                        out.write(buff, 0, buff.size)
                        i++
                        offSet = i * maxBlock
                    }
                } catch (e: Exception) {
                    throw CryptoException("加解密阀值为[$maxBlock]的数据时发生异常", e)
                }

                return out.toByteArray()
            }

        }
    }

}
