package com.example.data.model.api

import com.google.gson.annotations.SerializedName

data class MoveApiModel(
    @SerializedName("move") val move: MoveInfoApiModel,
)

data class MoveInfoApiModel(
    @SerializedName("name") val name: String,
)