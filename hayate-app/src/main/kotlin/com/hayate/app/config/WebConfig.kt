package com.hayate.app.config

import com.hayate.auth.interceptor.AuthTokenInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import java.nio.charset.StandardCharsets

/**
 * @author Flame
 * @date 2020-03-17 14:30
 */

@Configuration
class WebConfig @Autowired constructor(private val authTokenInterceptor: AuthTokenInterceptor) : WebMvcConfigurationSupport() {

    public override fun addInterceptors(registry: InterceptorRegistry) {
        val excludePathPatters = arrayOf(
            "/", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
            "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg",
            "/**/*.ttf", "/**/*.woff", "/**/*.eot", "/**/*.otf", "/**/*.woff2",
            "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        )

        // 认证拦截器
        registry.addInterceptor(authTokenInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(*excludePathPatters)
    }

    public override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        // 映射swagger-ui, 否则将无法访问
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/")
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Bean
    fun responseBodyConverter(): HttpMessageConverter<String> {
        return StringHttpMessageConverter(StandardCharsets.UTF_8)
    }

    public override fun configureMessageConverters(
        converters: MutableList<HttpMessageConverter<*>?>
    ) {
        super.configureMessageConverters(converters)
        converters.add(responseBodyConverter())
    }
}