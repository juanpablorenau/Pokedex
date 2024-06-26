package com.example.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.model.room.MoveDbModel
import com.example.data.model.room.MoveInfoDbModel
import com.example.data.model.room.PokemonBasicInfoDbModel
import com.example.data.model.room.PokemonDbModel
import com.example.data.model.room.PokemonInfoDbModel
import com.example.data.model.room.PokemonTypeDbModel
import com.example.data.model.room.StatDbModel

@Dao
interface PokedexDao {

    @Query("SELECT * FROM Pokemons")
    suspend fun getPokemons(): List<PokemonDbModel>

    @Query("SELECT * FROM Pokemons WHERE name = :name")
    suspend fun getPokemon(name: String): PokemonDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonDbModel>)

    @Transaction
    @Query("SELECT * FROM PokemonsBasicInfo WHERE name = :name")
    suspend fun getPokemonInfo(name: String): PokemonInfoDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonBasicInfo(pokemonBasicInfoDbModel: PokemonBasicInfoDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonTypes(pokemonTypes: List<PokemonTypeDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoves(pokemonMoves: List<MoveDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(pokemonMoves: List<StatDbModel>)

    @Query("SELECT * FROM MovesInfo WHERE name = :name")
    suspend fun getMoveInfo(name: String): MoveInfoDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoveInfo(move: MoveInfoDbModel)
}