package com.example.domain.usecase

import com.example.domain.model.entities.PokemonInfo
import com.example.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonInfoUseCase(private val repository: PokedexRepository) {

    fun invoke(name: String): Flow<PokemonInfo> =
        repository.getPokemonInfo(name)
}