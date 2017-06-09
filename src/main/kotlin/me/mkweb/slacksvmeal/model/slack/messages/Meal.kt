package me.mkweb.slacksvmeal.model.slack.messages

/**
 * @author Mario Kunz
 */
data class Meal(val title: String,
                val color: String,
                val fields: List<AttachmentField>)