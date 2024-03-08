package com.example.data.model.api

import com.example.domain.model.entities.PokemonInfo

data class PokemonInfoApiModel(
    val id: String,
    val name: String,
)

fun PokemonInfoApiModel.toDomainModel() =
    PokemonInfo(
        id = id,
        name = name
    )
