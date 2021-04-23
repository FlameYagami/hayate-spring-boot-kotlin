package com.hayate.common

import com.hayate.common.utils.RSAUtils.genKeyPair
import com.hayate.common.utils.RSAUtils.getPrivateKey
import com.hayate.common.utils.RSAUtils.getPublicKey
import java.security.Key

/**
 * Created by Flame on 2020/10/26.
 */
object RSAUtilsTest {
    private val rsaKeyMap: Map<String?, Key?> = genKeyPair()

    fun main(args: Array<String>) {
        try {
            val publicKey = getPublicKey(rsaKeyMap)
            val privateKey = getPrivateKey(rsaKeyMap)
            println("公钥: $publicKey")
            println("公钥长度: " + publicKey.length)
            println("私钥: $privateKey")
            println("私钥长度: " + privateKey.length)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}