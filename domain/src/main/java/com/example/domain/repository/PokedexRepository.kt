package com.example.domain.repository

import com.example.domain.model.entities.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    fun getPokemonInfo(name: String): Flow<PokemonInfo>
}