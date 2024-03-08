package com.example.data.di

import com.example.data.repository.PokedexRepository
import com.example.data.repository.impl.PokedexRepositoryImpl
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
    fun providesPokedexRepository(
        pokedexRemoteDataSource: PokedexRemoteDataSource,
        dispatcher: CoroutineDispatcher,
    ): PokedexRepository =
        PokedexRepositoryImpl(pokedexRemoteDataSource, dispatcher)
}