package me.mkweb.slacksvmeal.util

import me.mkweb.slacksvmeal.model.slack.SlackRequest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author Mario Kunz
 */

fun ServerResponse.BodyBuilder.json() = contentType(MediaType.APPLICATION_JSON)

fun ServerRequest.bodyToSlackRequestMono(): Mono<SlackRequest> {
    return this.body(BodyExtractors.toFormData())
            .map{
                SlackRequest(
                        token = it["token"]!![0] as String,
                        text = it["text"]!![0] as String
                )
            }
}

inline fun <reified T: Any> typeRef(): ParameterizedTypeReference<T> = object: ParameterizedTypeReference<T>(){}