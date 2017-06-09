package me.mkweb.slacksvmeal.model.oauth

/**
 * @author Mario Kunz
 */
data class IssuedTokenDto(val accessToken: String,
                          val scope: String)