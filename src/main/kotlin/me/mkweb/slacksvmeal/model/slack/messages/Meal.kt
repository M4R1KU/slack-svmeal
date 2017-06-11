package me.mkweb.slacksvmeal.model.slack.messages

import me.mkweb.slacksvmeal.model.slack.messages.partials.Attachment
import me.mkweb.slacksvmeal.model.slack.messages.partials.AttachmentField

/**
 * @author Mario Kunz
 */
data class Meal(val title: String,
                var color: String,
                val fields: List<AttachmentField>) : Attachment