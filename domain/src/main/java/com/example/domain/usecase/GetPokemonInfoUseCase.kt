package com.example.domain.usecase

import com.example.data.repository.PokedexRepository
import com.example.model.PokemonInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class GetPokemonInfoUseCase @Inject constructor(private val repository: PokedexRepository) {

    fun invoke(name: String): Flow<PokemonInfo> =
        repository.getPokemonInfo(name)
}