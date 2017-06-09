package me.mkweb.slacksvmeal.service.internal

import me.mkweb.slacksvmeal.model.slack.messages.Meal
import me.mkweb.slacksvmeal.service.MealService
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Mono

/**
 * @author Mario Kunz
 */
@Service
class DefaultMealService(val restTemplate: RestTemplate) : MealService {
    override fun getMealOfToday(): Mono<Meal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMealOfOffset(offset: Int): Mono<Meal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}