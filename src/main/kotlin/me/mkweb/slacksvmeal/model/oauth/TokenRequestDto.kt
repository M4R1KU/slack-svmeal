package me.mkweb.slacksvmeal.model.oauth

/**
 * @author Mario Kunz
 */
data class TokenRequestDto(val clientId: String,
                           val clientSecret: String,
                           val code: String)