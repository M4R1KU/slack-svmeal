package me.mkweb.slacksvmeal.service.internal

import me.mkweb.slacksvmeal.exception.RequestFailedException
import me.mkweb.slacksvmeal.model.mapper.MealMapper
import me.mkweb.slacksvmeal.model.slack.messages.Meal
import me.mkweb.slacksvmeal.model.svmeal.MealPlanDto
import me.mkweb.slacksvmeal.model.svmeal.Response
import me.mkweb.slacksvmeal.service.MealService
import me.mkweb.slacksvmeal.util.typeRef
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
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
        val requestEntity = RequestEntity<Any>(HttpMethod.GET, URI.create("$apiUrl/restaurant/bit/meal/$offset"))
        val response = restTemplate.exchange(requestEntity, typeRef<Response<MealPlanDto>>())
        val body = response.body
        if (response.statusCode != HttpStatus.OK || body.status != Response.Status.Ok) {
            return Flux.error(RequestFailedException(body.error ?: "Failed to get the mealplan from the svmeal-api"))
        }
        return body.data?.offers?.map(mealMapper::from)?.toFlux() ?: Flux.empty()
    }
}