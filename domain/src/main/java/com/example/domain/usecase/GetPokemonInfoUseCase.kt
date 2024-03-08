package com.example.domain.usecase

import com.example.data.repository.PokedexRepository
import com.example.model.PokemonInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonInfoUseCase @Inject constructor(private val repository: PokedexRepository) {

    operator fun invoke(name: String): Flow<PokemonInfo> =
        repository.getPokemonInfo(name)
}