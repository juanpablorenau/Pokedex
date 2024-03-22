package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.source.local.dao.AppDatabase
import com.example.data.source.local.dao.PokedexDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Pokedex.db"
        ).build()
    }

    @Singleton
    @Provides
    fun providePokedexDao(db: AppDatabase): PokedexDao = db.pokedexDao()
}
