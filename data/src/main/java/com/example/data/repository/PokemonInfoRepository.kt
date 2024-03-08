package com.example.data.repository


import com.example.model.Pokemon
import com.example.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokemonInfoRepository {
    fun getPokemonInfo(name: String): Flow<PokemonInfo>
}