package com.example.domain.usecase

import com.example.data.repository.PokemonRepository
import com.example.model.entities.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val repository: PokemonRepository) {

    operator fun invoke(name: String): Flow<Pokemon> =
        repository.getPokemon(name).flowOn(Dispatchers.IO)
}