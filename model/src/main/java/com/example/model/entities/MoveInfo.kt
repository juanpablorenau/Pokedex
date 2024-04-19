package com.example.model.entities

import com.example.model.entities.enums.PokemonType

data class MoveInfo(
    val id: Int = 0,
    val name: String = "bulbasaur",
    val accuracy: Int = 0,
    val power: Int = 0,
    val pp: Int = 0,
    val type: PokemonType = PokemonType.FIGHTING,
    val effects: List<String> = listOf("Es peligroso"),
)
