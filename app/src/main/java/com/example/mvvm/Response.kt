package com.example.mvvm

import com.example.mvvm.data.model.Product

sealed class Response<out T> {
    class Loading<T> : Response<T>() // ✅ Allow Loading to hold a generic type
    data class Success<T>(val data: T) : Response<T>()
    data class Failure<T>(val error: Throwable) : Response<T>() // ✅ Ensure Failure is generic
}
