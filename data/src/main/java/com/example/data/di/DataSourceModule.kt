package com.example.data.di

import com.example.data.source.remote.PokedexRemoteDataSource
import com.example.data.source.remote.api.PokedexApi
import com.example.data.source.remote.impl.PokedexRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesPokedexRemoteDataSource(pokedexApi: PokedexApi): PokedexRemoteDataSource =
        PokedexRemoteDataSourceImpl(pokedexApi)
}