package com.example.data.model.api

import com.example.model.entities.PokemonInfo
import com.example.model.entities.Stat
import com.example.model.entities.enums.PokemonType
import com.google.gson.annotations.SerializedName
import java.util.Locale

data class PokemonInfoApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("types") val types: List<TypeApiModel>,
    @SerializedName("base_experience") val baseExperience: String,
    @SerializedName("stats") val stats: List<StatApiModel>,
) {

    fun getImageUrl() =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" + "pokemon/other/official-artwork/$id.png"

    fun getPokemonTypes() = types.map {
        try {
            PokemonType.valueOf(it.type.name.uppercase())
        } catch (e: IllegalArgumentException) {
            PokemonType.UNKNOWN
        }
    }

    fun getFormattedWeight() = String.format(Locale.ENGLISH, "%.1f KG", weight.toFloat() / 10)
    fun getFormattedHeight() = String.format(Locale.ENGLISH, "%.1f M", height.toFloat() / 10)
    fun getPokemonStats() =
        stats.map { Stat(name = it.getFormattedStatName(), baseStat = it.baseState) }
}

fun PokemonInfoApiModel.toDomainModel() = PokemonInfo(
    id = id,
    name = name,
    height = getFormattedHeight(),
    weight = getFormattedWeight(),
    url = getImageUrl(),
    types = getPokemonTypes(),
    baseExperience = baseExperience,
    stats = getPokemonStats()
)
