package me.mkweb.slacksvmeal.model.slack.messages

import me.mkweb.slacksvmeal.model.slack.messages.partials.Attachment

class ErrorMessage(error: String = "An error occurred", attachments: List<Attachment>) : SlackMessage(error, attachments)