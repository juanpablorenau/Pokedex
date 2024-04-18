package com.example.data.model.room

import androidx.room.Entity

@Entity(tableName = "PokemonMoves", primaryKeys = ["pokemonId", "name"])
data class MoveDbModel(
    val pokemonId: Int,
    val name: String,
)

fun MoveDbModel.toDomainModel() = name

fun String.toDbModel(pokemonId: Int) = MoveDbModel(pokemonId = pokemonId, name = this)
