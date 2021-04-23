package com.hayate.auth

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author Flame
 * @date 2020-03-12 10:58
 */

@SpringBootApplication
class HayateAuthApplication

fun main(args: Array<String>) {
    SpringApplication.run(HayateAuthApplication::class.java, *args)
}