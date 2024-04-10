package com.example.data.model.api

import com.google.gson.annotations.SerializedName
import java.util.Locale

data class StatApiModel(
    @SerializedName("base_stat") val baseState: Int,
    @SerializedName("stat") val stat: StatInfoApiModel,
) {

    fun getFormattedStatName() =
        stat.name.split("-").joinToString(" ") { it.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
        } }
}

data class StatInfoApiModel(
    @SerializedName("name") val name: String,
)
