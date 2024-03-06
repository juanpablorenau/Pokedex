package com.example.domain.usecase.impl

import com.example.domain.model.entities.PokemonInfo
import com.example.domain.repository.PokedexRepository
import com.example.domain.usecase.GetPokemonInfoUseCase
import kotlinx.coroutines.flow.Flow

class GetPokemonInfoUseCaseImpl(private val repository: PokedexRepository) : GetPokemonInfoUseCase {

    override fun invoke(name: String): Flow<PokemonInfo> =
        repository.getPokemonInfo(name)
}