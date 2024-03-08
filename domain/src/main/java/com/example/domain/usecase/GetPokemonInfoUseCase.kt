package com.example.domain.usecase

import com.example.domain.model.entities.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface GetPokemonInfoUseCase {

    fun invoke(name: String): Flow<PokemonInfo>
}