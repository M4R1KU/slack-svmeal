package me.mkweb.slacksvmeal.model.mapper

import me.mkweb.slacksvmeal.model.slack.messages.Meal
import me.mkweb.slacksvmeal.model.slack.messages.partials.MealAttachement
import me.mkweb.slacksvmeal.model.slack.messages.partials.PriceAttachment
import me.mkweb.slacksvmeal.model.svmeal.MenuOfferDto
import org.springframework.stereotype.Component

@Component
class MealMapper : Mapper<MenuOfferDto, Meal> {
    override fun to(to: Meal): MenuOfferDto {
        throw UnsupportedOperationException("Can't map meal to menu offer")
    }

    override fun from(from: MenuOfferDto): Meal {
        return Meal(from.description, "", listOf(
                MealAttachement(from.title, from.trimmings.joinToString(separator = "\n")),
                PriceAttachment("Price in CHF", from.price.internalPrice.toString())
        ))
    }
}