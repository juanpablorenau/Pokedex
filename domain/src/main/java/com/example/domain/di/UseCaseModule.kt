package com.example.domain.di

import com.example.data.repository.PokedexRepository
import com.example.domain.usecase.GetPokemonInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun providesGetPokemonInfoUseCase(repository: PokedexRepository): GetPokemonInfoUseCase =
        GetPokemonInfoUseCase(repository)
}