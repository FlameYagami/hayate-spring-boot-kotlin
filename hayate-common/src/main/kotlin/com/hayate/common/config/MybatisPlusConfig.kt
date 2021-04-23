package com.hayate.common.config

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * @author Flame
 * @date 2020-03-12 14:37
 */
@Configuration
@EnableTransactionManagement
class MybatisPlusConfig {

    @Bean
    fun paginationInterceptor(): PaginationInterceptor {
        val page = PaginationInterceptor()
        page.setLimit(-1)
        page.setDialectType("mysql")
        return page
    }
}