package me.mkweb.slacksvmeal.model.svmeal

data class Response<T>(val status: Status,
                       val data: T,
                       val error: String) {

    enum class Status {
        OK, ERROR
    }
}