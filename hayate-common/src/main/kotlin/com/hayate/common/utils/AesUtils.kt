package com.hayate.common.utils


import org.slf4j.LoggerFactory
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES加密类
 *
 * @author Flame
 * @date 2020-03-10 10:27
 */

object AesUtils {

    private val log = LoggerFactory.getLogger(this::class.java)

    // 加密
    fun encrypt(sourceText: ByteArray, aesKey: String?, aesIv: String): ByteArray {
        return aesCrypto(sourceText, aesKey, aesIv, Cipher.ENCRYPT_MODE)
    }

    // 解密
    fun decrypt(sourceText: ByteArray, aesKey: String?, aesIv: String): ByteArray {
        return aesCrypto(sourceText, aesKey, aesIv, Cipher.DECRYPT_MODE)
    }

    private fun aesCrypto(sourceText: ByteArray, aesKey: String?, aesIv: String, cipherMode: Int): ByteArray {
        return try {
            // 判断Key是否正确
            if (aesKey == null) {
                log.error("AES key is null")
                return byteArrayOf()
            }
            // 判断Key是否为16位
            if (aesKey.length != 16) {
                log.error("AES key length is not 16")
                return byteArrayOf()
            }
            val secretKeySpec = SecretKeySpec(aesKey.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val iv = IvParameterSpec(aesIv.toByteArray())
            cipher.init(cipherMode, secretKeySpec, iv)
            cipher.doFinal(sourceText)
        } catch (e: Exception) {
            log.error("AES crypto error: ${e.message}")
            byteArrayOf()
        }
    }
}