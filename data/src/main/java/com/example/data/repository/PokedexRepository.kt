package com.example.data.repository


import com.example.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    fun getPokemonInfo(name: String): Flow<PokemonInfo>
}