package com.example.data.di

import com.example.data.repository.PokedexRepositoryImpl
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.domain.repository.PokedexRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesPokedexRepository(pokedexRemoteDataSource: PokedexRemoteDataSource): PokedexRepository =
        PokedexRepositoryImpl(pokedexRemoteDataSource)
}