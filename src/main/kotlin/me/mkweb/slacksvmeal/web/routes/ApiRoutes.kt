package me.mkweb.slacksvmeal.web.routes

import me.mkweb.slacksvmeal.web.handler.MealHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

/**
 * @author Mario Kunz
 */
@Configuration
class ApiRoutes(val mealHandler: MealHandler) {
    @Bean
    fun apiRouter() = router {
        ("/api/v1" and contentType(MediaType.APPLICATION_FORM_URLENCODED)).nest {
            POST("/meal", mealHandler::getMeal)
        }
    }
}