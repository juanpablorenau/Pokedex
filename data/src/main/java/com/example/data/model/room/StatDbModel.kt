package com.example.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.model.entities.Stat

@Entity(tableName = "PokemonStats", primaryKeys = ["pokemonId", "name"])
data class StatDbModel(
    val pokemonId: Int,
    val name: String,
    @ColumnInfo(name = "baseStat") val baseStat: Int,
)

fun StatDbModel.toDomainModel() = Stat(name = name, baseStat = baseStat)

fun Stat.toDbModel(pokemonId: Int) =
    StatDbModel(pokemonId = pokemonId, name = name, baseStat = baseStat)
