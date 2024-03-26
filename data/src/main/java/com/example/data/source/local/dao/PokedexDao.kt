package com.example.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.room.PokemonDbModel

@Dao
interface PokedexDao {

    @Query("SELECT * FROM Pokemons")
    suspend fun getPokemons(): List<PokemonDbModel>

    @Query("SELECT * FROM Pokemons WHERE name = :name")
    suspend fun getPokemon(name: String): PokemonDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonDbModel>)
}