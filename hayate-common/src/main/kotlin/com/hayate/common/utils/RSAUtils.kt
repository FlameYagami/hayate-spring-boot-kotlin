package com.hayate.common.utils


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.security.Key
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

/**
 * Created by Flame on 2020/06/03.
 */

object RSAUtils {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 加密算法RSA
     */
    private const val KEY_ALGORITHM = "RSA"

    /**
     * 获取公钥的key
     */
    private const val PUBLIC_KEY = "RSAPublicKey"

    /**
     * 获取私钥的key
     */
    private const val PRIVATE_KEY = "RSAPrivateKey"

    fun genKeyPair(): Map<String, Key> {
        val keyMap: MutableMap<String, Key> = HashMap()
        return try {
            val keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM)
            keyPairGenerator.initialize(1024)
            val keyPair = keyPairGenerator.generateKeyPair()
            keyMap[PUBLIC_KEY] = keyPair.public
            keyMap[PRIVATE_KEY] = keyPair.private
            keyMap
        } catch (e: Exception) {
            log.error("GenKeyPair error => ${e.message}")
            keyMap
        }
    }

    fun decryptByPrivateKey(bytes: ByteArray, privateKey: String): ByteArray {
        return try {
            val encodedKey = Base64.getMimeDecoder().decode(privateKey)
            val encodedKeySpec = PKCS8EncodedKeySpec(encodedKey)
            val rsaPrivateKey = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(encodedKeySpec) as RSAPrivateKey
            val cipher = Cipher.getInstance("RSA/ECB/NOPADDING")
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey)
            cipher.doFinal(bytes)
        } catch (e: Exception) {
            log.error("Decrypt by privateKey($privateKey) error: ${e.message}")
            byteArrayOf()
        }
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    fun getPrivateKey(keyMap: Map<String, Key>): String {
        val key = keyMap[PRIVATE_KEY]
        return getKey(key)
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    fun getPublicKey(keyMap: Map<String, Key>): String {
        val key = keyMap[PUBLIC_KEY]
        return getKey(key)
    }

    private fun getKey(key: Key?): String {
        return Base64.getMimeEncoder().encodeToString(key?.encoded)
    }
}