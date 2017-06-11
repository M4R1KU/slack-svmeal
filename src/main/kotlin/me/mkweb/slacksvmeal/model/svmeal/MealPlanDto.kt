package me.mkweb.slacksvmeal.model.svmeal

import java.time.LocalDate

data class MealPlanDto(val date: LocalDate,
                       val offers: List<MenuOfferDto>)