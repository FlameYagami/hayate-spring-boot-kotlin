package com.hayate.http

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class HayateHttpApplication

fun main(args: Array<String>) {
    SpringApplication.run(HayateHttpApplication::class.java, *args)
}