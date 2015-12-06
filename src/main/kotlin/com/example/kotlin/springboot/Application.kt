package com.example.kotlin.springboot

import com.querydsl.jpa.EclipseLinkTemplates
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import com.example.kotlin.springboot.security.config.SecurityConfigurerAdapter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@ImportResource("classpath:config/applicationContext.xml")
@SpringBootApplication
open class Application : SecurityConfigurerAdapter() {

    companion object {
        @JvmStatic public fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}