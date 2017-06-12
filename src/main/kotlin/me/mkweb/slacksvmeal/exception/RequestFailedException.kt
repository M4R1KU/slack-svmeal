package me.mkweb.slacksvmeal.exception

/**
 * @author Mario Kunz
 */
class RequestFailedException(override val message: String = "") : Exception(message)