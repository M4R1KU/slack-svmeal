package me.mkweb.slacksvmeal.model.slack.messages

/**
 * @author Mario Kunz
 */
data class DayMealMessage(val text: String,
                          val attachments: List<Meal>)