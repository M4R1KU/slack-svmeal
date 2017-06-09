package me.mkweb.slacksvmeal.web.routes

import me.mkweb.slacksvmeal.web.handler.OAuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

/**
 * @author Mario Kunz
 */
@Configuration
class OAuthRoutes(val oAuthHandler: OAuthHandler) {
    @Bean
    fun oathRoutes() = router {
        (accept(APPLICATION_JSON) and "/api/v1").nest {
            GET("/oauth", oAuthHandler::authorize)
        }
    }
}