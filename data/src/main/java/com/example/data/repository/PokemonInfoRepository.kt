package com.example.data.repository


import com.example.model.entities.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokemonInfoRepository {
    fun getPokemonInfo(name: String): Flow<PokemonInfo>
}