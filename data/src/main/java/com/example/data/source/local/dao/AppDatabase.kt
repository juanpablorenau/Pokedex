package com.example.data.source.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.room.MoveDbModel
import com.example.data.model.room.MoveInfoDbModel
import com.example.data.model.room.PokemonBasicInfoDbModel
import com.example.data.model.room.PokemonDbModel
import com.example.data.model.room.PokemonTypeDbModel
import com.example.data.model.room.StatDbModel


@Database(
    entities = [
        PokemonDbModel::class,
        PokemonBasicInfoDbModel::class,
        PokemonTypeDbModel::class,
        StatDbModel::class,
        MoveDbModel::class,
        MoveInfoDbModel::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokedexDao(): PokedexDao
}