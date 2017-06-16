package me.mkweb.slacksvmeal.web.handler

import me.mkweb.slacksvmeal.model.slack.messages.ErrorMessage
import me.mkweb.slacksvmeal.model.slack.messages.SlackMessage
import me.mkweb.slacksvmeal.model.slack.messages.partials.ErrorAttachment
import me.mkweb.slacksvmeal.service.MealService
import me.mkweb.slacksvmeal.util.MealColor
import me.mkweb.slacksvmeal.util.OffsetUtil.Companion.getValidOffsets
import me.mkweb.slacksvmeal.util.OffsetUtil.Companion.isValidOffset
import me.mkweb.slacksvmeal.util.bodyToSlackRequestMono
import me.mkweb.slacksvmeal.util.json
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.time.LocalDate

/**
 * @author Mario Kunz
 */
@Component
class MealHandler(val mealService: MealService) {
    fun getMeal(req: ServerRequest): Mono<ServerResponse> = ok().json().body(BodyInserters.fromPublisher(req.bodyToSlackRequestMono()
            .map { it.text.toInt() }
            .onErrorReturn({ it is NumberFormatException || it is NullPointerException }, 0)
            .flatMap {
                if (!isValidOffset(it)) {
                    val range: IntRange = getValidOffsets(LocalDate.now())
                    return@flatMap Mono.just(ErrorMessage("Not a valid offset", listOf(ErrorAttachment("Use offset between $range"))))
                }
                val colors = MealColor.values().toMutableList()
                mealService.getMealOfOffset(it)
                        .map({ meal ->
                            meal.color = colors.removeAt(colors.size.minus(1)).color
                            meal
                        }).collectList()
                        .map { attachments ->
                            SlackMessage.Builder("Showing the meal of %s", it)
                                    .addAttachments(attachments)
                                    .build()
                        }
                        .onErrorResume { ex -> ErrorMessage(attachments = listOf(ErrorAttachment(ex.message))).toMono() }
            }, SlackMessage::class.java))
}