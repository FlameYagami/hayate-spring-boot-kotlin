package com.hayate.common.config

import com.alibaba.druid.pool.DruidDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

/**
 * @author Flame
 * @date 2020-03-12 17:15
 */
@Configuration
class DruidConfig {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    fun druidDataSource(): DataSource {
        return DruidDataSource()
    }

    // 配置事物管理器
    @Bean(name = ["transactionManager"])
    fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(druidDataSource())
    }
}