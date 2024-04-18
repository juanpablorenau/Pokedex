package com.example.data.model.api

import com.example.model.entities.PokemonInfo
import com.example.model.entities.Stat
import com.example.model.entities.enums.getPokemonType
import com.google.gson.annotations.SerializedName

data class PokemonInfoApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("base_experience") val baseExperience: Int,
    @SerializedName("types") val types: List<TypeApiModel>,
    @SerializedName("stats") val stats: List<StatApiModel>,
    @SerializedName("moves") val moves: List<MoveApiModel>,
) {
    fun getImageUrl() =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    fun getPokemonTypes() = types.map { getPokemonType(it.type.name) }
    fun getPokemonStats() =
        stats.map { stat -> Stat(name = stat.getStatName(), baseStat = stat.baseState) }
    fun getPokemonMoves() = moves.map { it.move.name }
}

fun PokemonInfoApiModel.toDomainModel() = PokemonInfo(
    id = id,
    name = name,
    height = height,
    weight = weight,
    baseExperience = baseExperience,
    url = getImageUrl(),
    types = getPokemonTypes(),
    stats = getPokemonStats(),
    moves = getPokemonMoves()
)
