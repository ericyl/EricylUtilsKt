package com.ericyl.utils.util

import android.content.Context
import android.util.Base64
import com.ericyl.utils.cryptographical.rsa.RSACryptoImpl
import java.io.InputStream
import java.security.Key
import java.security.KeyFactory
import java.security.Signature
import java.security.cert.X509Certificate
import java.security.interfaces.RSAKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

object RSAUtils {

    private var publicKey: RSAPublicKey? = null
    private var certificate: X509Certificate? = null

    private const val RSA_ALGORITHM = "RSA"
    private const val RSA_PADDING = "RSA/ECB/PKCS1Padding"

    private const val SIGN_ALGORITHMS = "SHA1WithRSA"


    fun Context.getPublicKey(fileName: String) = getPublicKey(resources.assets.open(fileName))

    /**
     * 得到公钥
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPublicKey(fis: InputStream): RSAPublicKey? {
        if (publicKey == null) {
            synchronized(RSAPublicKey::class.java) {
                if (publicKey == null) {
                    val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)
                    fis.use {
                        val x509KeySpec = X509EncodedKeySpec(it.readBytes())
                        publicKey = keyFactory.generatePublic(x509KeySpec) as RSAPublicKey
                    }
                }
            }
        }
        return publicKey
    }

    fun <T> encrypt(t: T, text: ByteArray): String where T : Key, T : RSAKey {
        return Base64.encodeToString(RSACryptoImpl.create(t, RSA_PADDING).encrypt(text), Base64.NO_WRAP)
    }

    fun <T> decrypt(t: T, text: String): ByteArray where T : Key, T : RSAKey {
        return RSACryptoImpl.create(t, RSA_PADDING).decrypt(Base64.decode(text, Base64.NO_WRAP))
    }



    /**
     * 签名
     */
    @Throws(Exception::class)
    fun sign(privateKey: RSAPrivateKey, content: String): String {
        val signature = Signature.getInstance(SIGN_ALGORITHMS)
        signature.initSign(privateKey)
        signature.update(content.toByteArray(Charsets.UTF_8))
        val signed = signature.sign()
        return Base64.encodeToString(signed, Base64.NO_WRAP)
    }

    /**
     * 验证
     */
    @Throws(Exception::class)
    fun doCheck(publicKey: RSAPublicKey, content: String, sign: String): Boolean {
        val signature = Signature.getInstance(SIGN_ALGORITHMS)
        signature.initVerify(publicKey)
        signature.update(content.toByteArray(Charsets.UTF_8))
        return signature.verify(Base64.decode(sign, Base64.NO_WRAP))
    }

    /**
     * 校验证书
     */
    fun verify(certificate: X509Certificate, rsaPublicKey: RSAPublicKey): Boolean {
        return verifyCertificate(Date(), certificate, rsaPublicKey)
    }

    /**
     * 校验证书
     */
    private fun verifyCertificate(date: Date, certificate: X509Certificate, rsaPublicKey: RSAPublicKey): Boolean {
        return try {
            certificate.checkValidity(date)
            certificate.verify(rsaPublicKey)
            true
        } catch (e: Exception) {
            false
        }

    }


}