package me.mkweb.slacksvmeal.model.slack.messages

import me.mkweb.slacksvmeal.model.slack.messages.partials.Attachment
import me.mkweb.slacksvmeal.util.OffsetUtil

open class SlackMessage(val text: String,
                        val attachments: List<Attachment>) {
    class Builder(val text: String) {
        private var attachments: List<Attachment> = emptyList()

        constructor(formatString: String, offset: Int) : this(formatString.format(OffsetUtil.getDateByOffsetString(offset)))

        fun addAttachments(attachments: List<Attachment>): Builder {
            this.attachments = attachments
            return this
        }

        fun build(): SlackMessage {
            return SlackMessage(text, attachments)
        }
    }
}