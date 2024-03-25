package com.example.data.source.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.room.PokemonDbModel

@Database(entities = [PokemonDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokedexDao(): PokedexDao
}