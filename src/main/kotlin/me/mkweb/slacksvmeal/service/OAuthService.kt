package me.mkweb.slacksvmeal.service

import reactor.core.publisher.Mono

/**
 * @author Mario Kunz
 */
interface OAuthService {
    fun authorize(code: String): Mono<String>
}