package me.mkweb.slacksvmeal.model.mapper

interface Mapper<From, To> {
    fun from(from: From): To
    fun to(to: To): From
}