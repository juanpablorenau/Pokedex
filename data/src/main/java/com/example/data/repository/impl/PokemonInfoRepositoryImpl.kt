package com.example.data.repository.impl

import com.example.data.model.api.toDomainModel
import com.example.data.model.room.toDbModel
import com.example.data.model.room.toDomainModel
import com.example.data.repository.PokemonInfoRepository
import com.example.data.source.local.PokedexLocalDataSource
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.model.entities.Characteristics
import com.example.model.entities.MoveInfo
import com.example.model.entities.PokemonInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonInfoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokedexRemoteDataSource,
    private val localDataSource: PokedexLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : PokemonInfoRepository {

    override fun getPokemonInfo(name: String): Flow<PokemonInfo> = flow {
        val localPokemonInfo = localDataSource.getPokemonInfo(name)
        if (localPokemonInfo == null) {
            remoteDataSource.getPokemonInfo(name).toDomainModel().also { pokemonInfo ->
                localDataSource.insertPokemonInfo(pokemonInfo.toDbModel())
                emit(pokemonInfo)
            }
        } else {
            emit(localPokemonInfo.toDomainModel())
        }

    }.flowOn(dispatcher)

    override fun getPokemonCharacteristics(id: Int): Flow<Characteristics> = flow {
        remoteDataSource.getPokemonCharacteristics(id).also { emit(it.toDomainModel()) }
    }.flowOn(dispatcher)

    override fun getMoveInfo(name: String): Flow<MoveInfo> = flow {
        val localMove = localDataSource.getMoveInfo(name)
        if (localMove == null) {
            remoteDataSource.getMoveInfo(name).toDomainModel().also { move ->
                localDataSource.insertMoveInfo(move.toDbModel())
                emit(move)
            }
        } else {
            emit(localMove.toDomainModel())
        }
    }.flowOn(dispatcher)
}