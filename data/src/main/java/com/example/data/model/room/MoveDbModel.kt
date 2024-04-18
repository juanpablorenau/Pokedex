package com.example.data.model.room

import androidx.room.Entity

@Entity(tableName = "PokemonMoves", primaryKeys = ["pokemonId", "name"])
data class MoveDbModel(
    val pokemonId: Int,
    val name: String,
)