package com.example.domain.usecase

import com.example.data.repository.PokemonInfoRepository
import com.example.model.entities.Characteristics
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonCharacteristics @Inject constructor(private val repository: PokemonInfoRepository) {

    operator fun invoke(id: Int): Flow<Characteristics> = repository.getPokemonCharacteristics(id)
}