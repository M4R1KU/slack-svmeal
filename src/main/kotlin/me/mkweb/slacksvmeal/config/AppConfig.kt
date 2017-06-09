package me.mkweb.slacksvmeal.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

/**
 * @author Mario Kunz
 */
@Configuration
open class AppConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
                .additionalMessageConverters(listOf(
                        StringHttpMessageConverter(),
                        MappingJackson2HttpMessageConverter())
                ).build()
    }
}