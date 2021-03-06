package com.rvr.money.tracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@SpringBootApplication
class MoneyTrackerApplication

// Fix the CORS errors
@Bean
fun simpleCorsFilter(): FilterRegistrationBean<*> {
    val source = UrlBasedCorsConfigurationSource()
    val config = CorsConfiguration()
    config.allowCredentials = true
    // *** URL below needs to match the Vue client URL and port ***
    config.allowedOrigins = listOf("http://localhost:8080")
    config.allowedMethods = listOf("*")
    config.allowedHeaders = listOf("*")
    source.registerCorsConfiguration("/**", config)
    val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
    bean.order = Ordered.HIGHEST_PRECEDENCE
    return bean
}

fun main(args: Array<String>) {
    runApplication<MoneyTrackerApplication>(*args)
}
