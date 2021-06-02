package com.hayate.app

import com.hayate.common.utils.BcryptUtils

fun main() {
    val password = "123456"
    val encodedPassword = BcryptUtils.encode(password)
    val match = BcryptUtils.match(password, encodedPassword)
    println("password: $password")
    println("encodedPassword: $encodedPassword")
    println("match: $match")
}