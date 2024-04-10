package com.example.data.model.api

import com.example.model.utils.abbreviate
import com.example.model.utils.visualFormat
import com.google.gson.annotations.SerializedName

data class StatApiModel(
    @SerializedName("base_stat") val baseState: Int,
    @SerializedName("stat") val stat: StatInfoApiModel,
) {

    fun getStatName() =
        with(stat.name.visualFormat("-")) { if (contains(" ")) abbreviate(4) else this }
}

data class StatInfoApiModel(
    @SerializedName("name") val name: String,
)
