package com.yareach.socketjamsocket.common.extensions

import org.springframework.messaging.MessageDeliveryException
import org.springframework.web.server.ResponseStatusException

fun Any.throwErrorMessage(message: String): Nothing {
    val errorMessage = StringBuilder()
        .append("========= STOMP ERROR =========")
        .append('\n')
        .append(message)
        .append('\n')
        .append("this error thrown from '${this::class.java.name}'")
        .toString()
    println(errorMessage)
    throw MessageDeliveryException(errorMessage)
}

fun Any.throwErrorMessage(responseStatusException: ResponseStatusException): Nothing {
    val errorMessageString = "[${responseStatusException.javaClass.name}] ${responseStatusException.statusCode} - ${responseStatusException.message}"
    this.throwErrorMessage(errorMessageString)
}

fun Any.throwErrorMessage(exception: Exception): Nothing {
    val errorMessage = "[${exception.javaClass.name}] ${exception.message}"
    this.throwErrorMessage(errorMessage)
}
