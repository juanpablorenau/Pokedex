package com.example.data.utils

import android.util.Log
import retrofit2.Response

suspend fun <T> apiHandler(call: suspend () -> Response<T>): T =
    try {
        with(call()) {
            if (isSuccessful) body() ?: throw Exception("EmptyBody")
            else throw IllegalStateException("Call not successful")
        }
    } catch (e: Exception) {
        throw e
    }
