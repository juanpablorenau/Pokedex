package com.example.data.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.example.model.entities.PokemonInfo

data class PokemonInfoDbModel(
    @Embedded val pokemon: PokemonBasicInfoDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val types: List<PokemonTypeDbModel>,
    /*    @Relation(
            parentColumn = "id",
            entityColumn = "pokemonId"
        )
        val moves: List<MoveDbModel>,
        @Relation(
            parentColumn = "id",
            entityColumn = "pokemonId"
        )
        val stats: List<StatDbModel>,*/
)


fun PokemonInfoDbModel.toDomainModel() = PokemonInfo(
    id = pokemon.id,
    name = pokemon.name,
    height = pokemon.height,
    weight = pokemon.weight,
    url = pokemon.url,
    baseExperience = pokemon.baseExperience,
    types = types.map { it.toDomainModel() },
    /* moves = moves.map { it.name },
     stats = stats.map { it.toDomainModel() },*/
)

fun PokemonInfo.toDbModel() = PokemonInfoDbModel(
    pokemon = PokemonBasicInfoDbModel(
        id = id,
        name = name,
        height = height,
        weight = weight,
        url = url,
        baseExperience = baseExperience
    ),
    types = types.map { it.toDbModel(id) },
    /* moves = moves.map { MoveDbModel(id, it) },
     stats = stats.map { it.toDbModel(id) }*/
)
