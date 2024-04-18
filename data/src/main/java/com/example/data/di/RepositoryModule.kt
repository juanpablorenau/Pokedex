package com.example.data.di

import com.example.data.repository.PokemonInfoRepository
import com.example.data.repository.PokemonRepository
import com.example.data.repository.impl.PokemonInfoRepositoryImpl
import com.example.data.repository.impl.PokemonRepositoryImpl
import com.example.data.source.local.PokedexLocalDataSource
import com.example.data.source.remote.PokedexRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesPokemonRepository(
        pokedexRemoteDataSource: PokedexRemoteDataSource,
        pokedexLocalDataSource: PokedexLocalDataSource,
        dispatcher: CoroutineDispatcher,
    ): PokemonRepository =
        PokemonRepositoryImpl(pokedexRemoteDataSource, pokedexLocalDataSource, dispatcher)

    @Provides
    fun providesPokemonInfoRepository(
        pokedexRemoteDataSource: PokedexRemoteDataSource,
        pokedexLocalDataSource: PokedexLocalDataSource,
        dispatcher: CoroutineDispatcher,
    ): PokemonInfoRepository =
        PokemonInfoRepositoryImpl(pokedexRemoteDataSource, pokedexLocalDataSource, dispatcher)
}