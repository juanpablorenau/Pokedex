package com.example.data.utils

import com.example.data.model.api.CallNotSuccessfulException
import com.example.data.model.api.EmptyBodyException
import retrofit2.Response

suspend fun <T> apiHandler(call: suspend () -> Response<T>): T =
    try {
        with(call()) {
            if (isSuccessful) body() ?: throw EmptyBodyException()
            else throw CallNotSuccessfulException()
        }
    } catch (e: Exception) {
        throw e
    }
