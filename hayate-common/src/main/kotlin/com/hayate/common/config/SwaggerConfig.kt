package com.hayate.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * @author Flame
 * @date 2020-03-12 16:11
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Value("\${swagger.show}")
    private val swaggerShow = false

    @Value("\${swagger.basePackage}")
    private lateinit var basePackagePath: String

    // 配置api信息
    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackagePath))
                .paths(PathSelectors.any())
                .build()
    }

    // 应用描述信息
    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("")
                .description("")
                .termsOfServiceUrl("")
                .contact(Contact("", "", ""))
                .version("1.0").build()
    }
}