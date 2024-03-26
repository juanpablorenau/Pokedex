package com.example.domain.di

import com.example.data.repository.PokemonInfoRepository
import com.example.data.repository.PokemonRepository
import com.example.domain.usecase.GetPokemonInfoUseCase
import com.example.domain.usecase.GetPokemonUseCase
import com.example.domain.usecase.GetPokemonsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun providesGetPokemonsUseCase(repository: PokemonRepository): GetPokemonsUseCase =
        GetPokemonsUseCase(repository)

    @Provides
    fun providesGetPokemonInfoUseCase(repository: PokemonInfoRepository): GetPokemonInfoUseCase =
        GetPokemonInfoUseCase(repository)

    @Provides
    fun providesGetPokemonUseCase(repository: PokemonRepository): GetPokemonUseCase =
        GetPokemonUseCase(repository)
}