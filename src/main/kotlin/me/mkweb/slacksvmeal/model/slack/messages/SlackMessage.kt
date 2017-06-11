package me.mkweb.slacksvmeal.model.slack.messages

import me.mkweb.slacksvmeal.model.slack.messages.partials.Attachment

open class SlackMessage(val text: String,
                        val attachments: List<Attachment>)