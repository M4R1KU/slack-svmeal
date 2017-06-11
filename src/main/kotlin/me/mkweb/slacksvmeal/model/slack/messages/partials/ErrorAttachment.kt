package me.mkweb.slacksvmeal.model.slack.messages.partials

data class ErrorAttachment(val title: String,
                           val color: String = "#f00") : Attachment