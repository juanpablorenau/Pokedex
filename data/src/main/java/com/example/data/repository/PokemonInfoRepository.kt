package com.example.data.repository


import com.example.model.entities.Characteristics
import com.example.model.entities.MoveInfo
import com.example.model.entities.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokemonInfoRepository {
    fun getPokemonInfo(name: String): Flow<PokemonInfo>
    fun getPokemonCharacteristics(id: Int) : Flow<Characteristics>
    fun getMoveInfo(name: String) : Flow<MoveInfo>
}