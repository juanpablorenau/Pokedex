package com.example.data.model.api

import com.example.model.entities.Characteristics
import com.example.model.utils.orEmptyString
import com.google.gson.annotations.SerializedName

data class CharacteristicsApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("descriptions") val descriptions: List<DescriptionApiModel>,
) {

    fun getDescription() =
        descriptions.find { it.language.name == "en" }?.description.orEmptyString()
}

data class DescriptionApiModel(
    @SerializedName("description") val description: String,
    @SerializedName("language") val language: LanguageApiModel,
)

data class LanguageApiModel(
    @SerializedName("name") val name: String,
)

fun CharacteristicsApiModel.toDomainModel() =
    Characteristics(
        id = id,
        description = getDescription()
    )