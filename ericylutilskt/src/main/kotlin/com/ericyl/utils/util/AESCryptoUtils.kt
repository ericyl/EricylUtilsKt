package com.ericyl.utils.util

import android.content.Context
import android.util.Base64
import com.ericyl.utils.cryptographical.aes.AESCryptoImpl
import com.ericyl.utils.cryptographical.aes.AESCryptoKey
import com.ericyl.utils.cryptographical.exception.CryptoException
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * @author ericyl
 */
private const val SIZE_32 = 32

fun getRandomBytes(size: Int = SIZE_32): ByteArray {
    val bytes = ByteArray(size)
    SecureRandom().nextBytes(bytes)
    return bytes
}

fun getSecretKey(password: String, salt: ByteArray, iterationCount: Int = 1000, keyLength: Int = SIZE_32 * 8): SecretKey {
    try {
        val keySpec = PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength)
        val keyBytes = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(keySpec).encoded
        return SecretKeySpec(keyBytes, "AES")
    } catch (e: Exception) {
        throw CryptoException(e)
    }
}

//@Deprecated("This function is deprecated", ReplaceWith("getSecretKey(String, ByteArray, Int, Int)"))
//private fun getSecretKey(password: String): SecretKey {
//    try {
//        val keyGen = KeyGenerator.getInstance("AES")
//        val secureRandom = SecureRandom.getInstance("SHA1PRNG")
//        secureRandom.setSeed(password.toByteArray(charset("UTF-8")))
//        keyGen.init(128, secureRandom)
//        return keyGen.generateKey()
//    } catch (e: Exception) {
//        throw CryptoException(e)
//    }
//}

fun Context.saveToKeyStore(key: SecretKey, fileName: String, storePwd: String, entryAlias: String, entryPwd: String, type: String = KeyStore.getDefaultType()) {
    try {
        val keyStore = KeyStore.getInstance(type)
        keyStore.load(null)
        val secretKeyEntry = KeyStore.SecretKeyEntry(key)
        keyStore.setEntry(entryAlias, secretKeyEntry, KeyStore.PasswordProtection(entryPwd.toCharArray()))
        val fos = FileOutputStream("${getExternalFilesDir("file")}${File.separator}$fileName")
        fos.use {
            keyStore.store(fos, storePwd.toCharArray())
        }
    } catch (e: Exception) {
        throw CryptoException(e)
    }
}

fun Context.saveKey(key: ByteArray, fileName: String) {
    try {
        val fos = FileOutputStream("${getExternalFilesDir("file")}${File.separator}$fileName")
        fos.use {
            fos.write(key)
        }
    } catch (e: Exception) {
        throw CryptoException(e)
    }
}

fun Context.readSecretKeyFromAssets(fileName: String, storePwd: String, entryAlias: String, entryPwd: String, type: String = KeyStore.getDefaultType()) = readSecretKey(resources.assets.open(fileName), storePwd, entryAlias, entryPwd, type)


fun readSecretKey(fis: InputStream, storePwd: String, entryAlias: String, entryPwd: String, type: String = KeyStore.getDefaultType()): SecretKey {
    fis.use {
        val keyStore = KeyStore.getInstance(type)
        keyStore.load(fis, storePwd.toCharArray())
        val secretKeyEntry = keyStore.getEntry(entryAlias, KeyStore.PasswordProtection(entryPwd.toCharArray())) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey
    }
}

fun encrypt(secretKey: SecretKey, iv: ByteArray, originalText: String) = Base64.encodeToString(AESCryptoImpl.create(AESCryptoKey(secretKey).getKey().encoded, iv).encrypt(originalText.toByteArray(charset("UTF-8"))), Base64.NO_WRAP)

fun encrypt(key: ByteArray, iv: ByteArray, originalText: String) = Base64.encodeToString(AESCryptoImpl.create(key, iv).encrypt(originalText.toByteArray(charset("UTF-8"))), Base64.NO_WRAP)

fun decrypt(secretKey: SecretKey, iv: ByteArray, cipherText: String) = String(AESCryptoImpl.create(secretKey.encoded, iv).decrypt(Base64.decode(cipherText, Base64.NO_WRAP)), charset("UTF-8"))

fun decrypt(key: ByteArray, iv: ByteArray, cipherText: String) = String(AESCryptoImpl.create(key, iv).decrypt(Base64.decode(cipherText, Base64.NO_WRAP)), charset("UTF-8"))