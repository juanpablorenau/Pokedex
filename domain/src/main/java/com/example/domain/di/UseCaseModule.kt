package com.example.domain.di

import com.example.domain.repository.PokedexRepository
import com.example.domain.usecase.GetPokemonInfoUseCase
import com.example.domain.usecase.impl.GetPokemonInfoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesGetPokemonInfoUseCase(repository: PokedexRepository): GetPokemonInfoUseCase =
        GetPokemonInfoUseCaseImpl(repository)
}