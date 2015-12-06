package com.example.kotlin.springboot.security.config

import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

open class SecurityConfigurerAdapter : WebMvcConfigurerAdapter() {

    @Bean
    open fun authorizeInterceptor() : AuthorizeInterceptor {
        return AuthorizeInterceptor()
    }

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry?.addInterceptor(authorizeInterceptor())
    }
}