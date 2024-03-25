package com.example.data.di

import com.example.data.source.local.PokedexLocalDataSource
import com.example.data.source.local.dao.PokedexDao
import com.example.data.source.local.impl.PokedexLocalDataSourceImpl
import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.data.source.remote.api.PokedexApi
import com.example.data.source.remote.impl.PokedexRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesPokedexRemoteDataSource(
        pokedexApi: PokedexApi,
        dispatcher: CoroutineDispatcher,
    ): PokedexRemoteDataSource = PokedexRemoteDataSourceImpl(pokedexApi, dispatcher)

    @Provides
    fun providesPokedexLocalDataSource(
        pokedexDao: PokedexDao,
        dispatcher: CoroutineDispatcher,
    ): PokedexLocalDataSource = PokedexLocalDataSourceImpl(pokedexDao, dispatcher)
}