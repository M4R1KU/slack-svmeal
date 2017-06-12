package me.mkweb.slacksvmeal.model.svmeal

data class Response<T>(val status: Status = Status.Unknown,
                           val data: T? = null,
                           val error: String? = "") {

    enum class Status {
        Ok, Error, Unknown
    }
}