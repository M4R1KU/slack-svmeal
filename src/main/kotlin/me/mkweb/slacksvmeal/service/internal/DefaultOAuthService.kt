package me.mkweb.slacksvmeal.service.internal

import me.mkweb.slacksvmeal.model.oauth.IssuedTokenDto
import me.mkweb.slacksvmeal.model.oauth.TokenRequestDto
import me.mkweb.slacksvmeal.service.OAuthService
import org.apache.commons.lang.StringUtils.isNotBlank
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Mono

/**
 * @author Mario Kunz
 */
@Service
class DefaultOAuthService(val restTemplate: RestTemplate) : OAuthService {
    val LOGGER: Logger = LoggerFactory.getLogger(DefaultOAuthService::class.java)

    @Value("\${slack.client.id}")
    lateinit var clientId: String
    @Value("\${slack.client.secret}")
    lateinit var clientSecret: String
    @Value("\${slack.oauth.url}")
    lateinit var slackOAuthUrl: String

    override fun authorize(code: String): Mono<String> {
        val tokenRequest = TokenRequestDto(clientId, clientSecret, code)
        val response: ResponseEntity<IssuedTokenDto> = try {
            restTemplate.postForEntity(slackOAuthUrl, tokenRequest, IssuedTokenDto::class.java)
        } catch (re: RestClientException) {
            LOGGER.error("Failed to get token from slack", re)
            return Mono.error(re)
        }
        if (response.statusCode != HttpStatus.OK) {
            return Mono.empty()
        }
        val issuedTokenDto = response.body
        return Mono.just(if (isNotBlank(issuedTokenDto.accessToken)) issuedTokenDto.accessToken else null)
    }
}