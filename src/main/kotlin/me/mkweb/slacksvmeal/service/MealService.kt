package me.mkweb.slacksvmeal.service

import me.mkweb.slacksvmeal.model.slack.messages.Meal
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author Mario Kunz
 */
interface MealService {
    fun getMealOfToday(): Mono<Meal>
    fun getMealOfOffset(offset: Int): Flux<Meal>
}