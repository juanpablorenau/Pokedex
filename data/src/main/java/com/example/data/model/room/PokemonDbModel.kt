package com.example.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.entities.Pokemon

@Entity(tableName = "Pokemons")
data class PokemonDbModel(
    @PrimaryKey val name: String,
    @ColumnInfo("url") val url: String,
)

fun PokemonDbModel.toDomainModel() = Pokemon(name = name, url = url)

fun Pokemon.toDbModel() = PokemonDbModel(name = name, url = url)