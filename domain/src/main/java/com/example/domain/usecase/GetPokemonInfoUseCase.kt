package com.example.domain.usecase

import com.example.data.repository.PokemonInfoRepository
import com.example.model.entities.PokemonInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonInfoUseCase @Inject constructor(private val repository: PokemonInfoRepository) {

    fun invoke(name: String): Flow<PokemonInfo> =
        repository.getPokemonInfo(name)
}