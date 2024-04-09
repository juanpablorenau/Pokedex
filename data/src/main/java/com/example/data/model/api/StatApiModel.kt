package com.example.data.model.api

import com.google.gson.annotations.SerializedName

data class StatApiModel(
    @SerializedName("base_stat") val baseState: Int,
    @SerializedName("stat") val stat: StatInfoApiModel,
)

data class StatInfoApiModel(
    @SerializedName("name") val name: String,
)
