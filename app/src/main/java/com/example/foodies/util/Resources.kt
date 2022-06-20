package com.example.foodies.util

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resources<T>(data)
    class Error<T>(message: String) : Resources<T>(null, message)
    class Loading<T> : Resources<T>()
}