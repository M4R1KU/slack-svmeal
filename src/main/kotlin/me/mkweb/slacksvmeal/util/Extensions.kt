package me.mkweb.slacksvmeal.util

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse

/**
 * @author Mario Kunz
 */

fun ServerResponse.BodyBuilder.json() = contentType(MediaType.APPLICATION_JSON)
