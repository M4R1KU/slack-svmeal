package me.mkweb.slacksvmeal.model.svmeal

data class MenuOfferDto(val title: String = "",
                        val description: String = "",
                        val trimmings: List<String> = emptyList(),
                        val price: PriceDto = PriceDto(),
                        val provenance: String = "")