package com.example.domain.usecase

import com.example.data.repository.PokemonInfoRepository
import com.example.model.entities.MoveInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoveInfoUseCase @Inject constructor(private val repository: PokemonInfoRepository) {

    operator fun invoke(name: String): Flow<MoveInfo> = repository.getMoveInfo(name)
}