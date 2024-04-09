package com.example.model.entities

import com.example.model.entities.enums.PokemonType

data class PokemonInfo(
    val id: String = "",
    val name: String = "",
    val height: String = "",
    val weight: String = "",
    val url: String = "",
    val types: List<PokemonType> = listOf(PokemonType.UNKNOWN),
    val stats: List<Stat> = listOf(),
    val baseExperience: String = "",
)

fun PokemonInfo.getFormattedId() = when {
    id.toInt() < 10 -> "#00$id"
    id.toInt() < 100 -> "#0$id"
    else -> "#$id"
}

data class Stat(
    val name: String = "",
    val baseStat: Int = 0,
)
