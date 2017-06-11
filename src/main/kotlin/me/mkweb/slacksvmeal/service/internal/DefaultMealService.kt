package me.mkweb.slacksvmeal.service.internal

import me.mkweb.slacksvmeal.model.mapper.MealMapper
import me.mkweb.slacksvmeal.model.slack.messages.Meal
import me.mkweb.slacksvmeal.model.svmeal.MealPlanDto
import me.mkweb.slacksvmeal.service.MealService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

/**
 * @author Mario Kunz
 */
@Service
class DefaultMealService(val restTemplate: RestTemplate,
                         val mealMapper: MealMapper) : MealService {
    @Value("\${svmeal.api.url}")
    lateinit var apiUrl: String

    override fun getMealOfToday(): Mono<Meal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMealOfOffset(offset: Int): Flux<Meal> {
        return Flux.fromIterable(
                restTemplate.getForObject(URI("$apiUrl/restaurant/bit/meal/$offset"), MealPlanDto::class.java).offers
                        .map { mealMapper.from(it) }
        )
    }
}