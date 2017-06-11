package me.mkweb.slacksvmeal.model.slack

import me.mkweb.slacksvmeal.annotations.NoArg

/**
 * @author Mario Kunz
 */
@NoArg
data class SlackRequest(val token: String = "",
                        val text: String = "") {
}