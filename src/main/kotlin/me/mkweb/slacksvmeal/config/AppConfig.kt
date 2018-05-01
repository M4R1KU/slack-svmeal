package me.mkweb.slacksvmeal.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.net.InetSocketAddress
import java.net.Proxy

/**
 * @author Mario Kunz
 */
@Configuration
open class AppConfig {
    @Bean
    fun restTemplate(requestFactory: ClientHttpRequestFactory): RestTemplate = RestTemplateBuilder()
            .additionalMessageConverters(listOf(
                    StringHttpMessageConverter(),
                    MappingJackson2HttpMessageConverter())
            ).requestFactory { requestFactory }
            .build()

    @Bean
    @Profile("!bit")
    fun requestFactory(): ClientHttpRequestFactory = SimpleClientHttpRequestFactory()

    @Bean
    @Profile("bit")
    fun proxyRequestFactory(): ClientHttpRequestFactory {
        val factory = SimpleClientHttpRequestFactory()
        val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("proxy.efd.admin.ch", 8080))
        factory.setProxy(proxy)
        return factory
    }
}