package com.example.model.entities

import com.example.model.entities.enums.PokemonType

data class PokemonInfo(
    val id: Int = 0,
    val name: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val url: String = "",
    val baseExperience: Int = 0,
    val types: List<PokemonType> = listOf(PokemonType.UNKNOWN),
    val stats: List<Stat> = listOf(),
    val moves: List<String> = listOf(),
)

fun PokemonInfo.getFormattedId() = when {
    id < 10 -> "#00$id"
    id < 100 -> "#0$id"
    else -> "#$id"
}

data class Stat(
    val name: String = "",
    val baseStat: Int = 0,
)
