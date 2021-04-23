package com.hayate.common

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author Flame
 * @date 2020-03-12 11:00
 */
@SpringBootApplication
class HayateCommonApplication

fun main(args: Array<String>) {
    SpringApplication.run(HayateCommonApplication::class.java, *args)
}