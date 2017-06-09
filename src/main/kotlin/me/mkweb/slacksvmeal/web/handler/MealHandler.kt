package me.mkweb.slacksvmeal.web.handler

import me.mkweb.slacksvmeal.model.slack.SlackRequest
import me.mkweb.slacksvmeal.service.MealService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

/**
 * @author Mario Kunz
 */
@Component
class MealHandler(val mealService: MealService) {
    fun getMeal(req: ServerRequest) : Mono<ServerResponse> {
        req.bodyToMono(SlackRequest::class)
        return ServerResponse.ok().body(BodyInserters.fromObject(null))
    }
}