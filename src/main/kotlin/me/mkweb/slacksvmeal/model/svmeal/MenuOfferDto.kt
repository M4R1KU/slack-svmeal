package me.mkweb.slacksvmeal.model.svmeal

data class MenuOfferDto(val title: String,
                        val description: String,
                        val trimmings: List<String>,
                        val price: PriceDto,
                        val provenance: String)