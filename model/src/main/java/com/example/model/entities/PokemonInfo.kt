package com.example.model.entities

import com.example.model.entities.enums.PokemonType

data class PokemonInfo(
    val id: String = "",
    val name: String = "",
    val weight: String = "",
    val url: String = "",
    val types: List<PokemonType> = listOf(PokemonType.UNKNOWN),
)
