package com.example.data.source.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.room.MoveInfoDbModel
import com.example.data.model.room.PokemonDbModel
import com.example.data.model.room.PokemonInfoDbModel
import com.example.data.model.room.StatDbModel


@Database(
    entities = [PokemonDbModel::class, MoveInfoDbModel::class, PokemonInfoDbModel::class, StatDbModel::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokedexDao(): PokedexDao
}