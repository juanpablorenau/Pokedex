package com.example.data.model.api

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<T>,
)
