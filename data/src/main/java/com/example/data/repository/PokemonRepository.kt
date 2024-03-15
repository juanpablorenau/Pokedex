package com.example.data.repository

import com.example.model.entities.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemons() : Flow<List<Pokemon>>
}