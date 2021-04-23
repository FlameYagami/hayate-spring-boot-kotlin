package com.hayate.app

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.hayate"])
@MapperScan("com.hayate.app.mapper*")
class HayateApplication

fun main(args: Array<String>) {
    runApplication<HayateApplication>(*args)
}