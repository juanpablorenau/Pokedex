package com.example.data.model.room

import androidx.room.Entity
import com.example.model.entities.enums.PokemonType
import com.example.model.entities.enums.getPokemonType

@Entity(tableName = "PokemonTypes", primaryKeys = ["pokemonId", "type"])
data class PokemonTypeDbModel(
    val pokemonId: Int,
    val type: String,
)

fun PokemonTypeDbModel.toDomainModel() = getPokemonType(type)

fun PokemonType.toDbModel(pokemonId: Int) =
    PokemonTypeDbModel(pokemonId = pokemonId, type = type)
