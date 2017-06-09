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
    fun oathRoutes() = router {
        (accept(MediaType.APPLICATION_JSON) and "/api/v1").nest {
            GET("/meal", mealHandler::getMeal)
        }
    }
}