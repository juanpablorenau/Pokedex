package com.example.data.model.api

import com.google.gson.annotations.SerializedName

data class TypeApiModel(
    @SerializedName("slot") val slot: String,
    @SerializedName("type") val type: TypeInfoApiModel,
)

data class TypeInfoApiModel(
    @SerializedName("name") val name: String,
)
