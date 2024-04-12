package com.example.data.model.api

import com.example.model.entities.PokemonInfo
import com.example.model.entities.Stat
import com.example.model.entities.enums.PokemonType
import com.google.gson.annotations.SerializedName

data class PokemonInfoApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("types") val types: List<TypeApiModel>,
    @SerializedName("base_experience") val baseExperience: Int,
    @SerializedName("stats") val stats: List<StatApiModel>,
) {

    fun getImageUrl() =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    fun getPokemonTypes() = types.map {
        try {
            PokemonType.valueOf(it.type.name.uppercase())
        } catch (e: IllegalArgumentException) {
            PokemonType.UNKNOWN
        }
    }
    fun getPokemonStats() = stats.map { Stat(name = it.getStatName(), baseStat = it.baseState) }
}

fun PokemonInfoApiModel.toDomainModel() = PokemonInfo(
    id = id,
    name = name,
    height = height,
    weight = weight,
    url = getImageUrl(),
    types = getPokemonTypes(),
    baseExperience = baseExperience,
    stats = getPokemonStats()
)
