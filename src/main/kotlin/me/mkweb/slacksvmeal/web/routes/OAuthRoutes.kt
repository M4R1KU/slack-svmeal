package me.mkweb.slacksvmeal.web.routes

import me.mkweb.slacksvmeal.web.handler.OAuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

/**
 * @author Mario Kunz
 */
@Configuration
class OAuthRoutes(val oAuthHandler: OAuthHandler) {
    @Bean
    fun ouathRouter() = router {
        ("/api/v1" and contentType(MediaType.APPLICATION_JSON)).nest {
            GET("/oauth", oAuthHandler::authorize)
        }
    }
}