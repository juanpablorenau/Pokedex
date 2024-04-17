package com.example.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.model.entities.PokemonInfo
import com.example.model.entities.Stat
import com.example.model.entities.enums.PokemonType

@Entity(tableName = "PokemonsInfo")
data class PokemonInfoDbModel(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "baseExperience") val baseExperience: Int,
    @ColumnInfo(name = "types") val types: List<String>,
    @ColumnInfo(name = "moves") val moves: List<String>,
)

@Entity(tableName = "PokemonStats")
data class StatDbModel(
    @PrimaryKey val pokemonId: Int,
    @PrimaryKey val name: String,
    @ColumnInfo(name = "baseStat") val baseStat: Int,
)

data class PokemonInfoEmbedded(
    @Embedded val pokemon: PokemonInfoDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val stats: List<StatDbModel>,
)

fun PokemonInfoEmbedded.toDomainModel() = PokemonInfo(
    id = pokemon.id,
    name = pokemon.name,
    height = pokemon.height,
    weight = pokemon.weight,
    url = pokemon.url,
    baseExperience = pokemon.baseExperience,
    moves = pokemon.moves,
    types = pokemon.types.map { PokemonType.valueOf(it.uppercase()) },
    stats = stats.map { it.toDomainModel() },
)

private fun StatDbModel.toDomainModel() = Stat(
    name = name,
    baseStat = baseStat
)

fun PokemonInfo.toDbModel() = PokemonInfoEmbedded(
    pokemon = PokemonInfoDbModel(
        id = id,
        name = name,
        height = height,
        weight = weight,
        url = url,
        baseExperience = baseExperience,
        moves = moves,
        types = types.map { it.type },
    ),
    stats = stats.map { it.toDbModel(id) }
)

private fun Stat.toDbModel(pokemonId: Int) = StatDbModel(
    pokemonId = pokemonId,
    name = name,
    baseStat = baseStat
)
