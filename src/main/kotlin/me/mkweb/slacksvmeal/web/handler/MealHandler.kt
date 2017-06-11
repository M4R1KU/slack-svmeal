package me.mkweb.slacksvmeal.web.handler

import me.mkweb.slacksvmeal.model.slack.SlackRequest
import me.mkweb.slacksvmeal.model.slack.messages.ErrorMessage
import me.mkweb.slacksvmeal.model.slack.messages.SlackMessage
import me.mkweb.slacksvmeal.model.slack.messages.partials.ErrorAttachment
import me.mkweb.slacksvmeal.service.MealService
import me.mkweb.slacksvmeal.util.MealColor
import me.mkweb.slacksvmeal.util.OffsetUtil.Companion.getValidOffsets
import me.mkweb.slacksvmeal.util.OffsetUtil.Companion.isValidOffset
import me.mkweb.slacksvmeal.util.json
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import java.time.LocalDate

/**
 * @author Mario Kunz
 */
@Component
class MealHandler(val mealService: MealService) {
    fun getMeal(req: ServerRequest): Mono<ServerResponse> {
        return ok().json().body(BodyInserters.fromPublisher(req.bodyToMono(SlackRequest::class)
                .map { it.text.toInt() }
                .onErrorReturn({ it is NumberFormatException }, 0)
                .flatMap {
                    if (!isValidOffset(it)) {
                        val range: IntRange = getValidOffsets(LocalDate.now())
                        Mono.just(ErrorMessage("Not a valid offset", listOf(ErrorAttachment("Use offset between $range"))))
                    }
                    val colors = MealColor.values().toMutableList()
                    mealService.getMealOfOffset(it)
                            .map({ meal ->
                                meal.color = colors.removeAt(colors.size.minus(1)).color
                                meal
                            }).collectList()
                            .map { attachments ->
                                SlackMessage("", attachments)
                            }
                }, SlackMessage::class.java))
    }
}