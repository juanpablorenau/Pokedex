package com.example.domain.usecase

import com.example.data.repository.PokemonRepository
import com.example.model.entities.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(private val repository: PokemonRepository) {

    operator fun invoke(): Flow<List<Pokemon>> = repository.getPokemons().flowOn(Dispatchers.IO)
}