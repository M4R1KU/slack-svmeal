package me.mkweb.slacksvmeal.web.handler

import me.mkweb.slacksvmeal.service.OAuthService
import me.mkweb.slacksvmeal.util.json
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono

/**
 * @author Mario Kunz
 */
@Component
class OAuthHandler(val oAuthService: OAuthService) {
    fun authorize(req: ServerRequest): Mono<ServerResponse> {
        val code: String = try {
            req.pathVariable("code")
        } catch(e: IllegalArgumentException) {
            return Mono.from { badRequest() }
        }
        return oAuthService.authorize(code)
                .flatMap({ ok().json().body(fromObject(it)) })
    }
}