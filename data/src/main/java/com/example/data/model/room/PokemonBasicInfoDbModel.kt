package com.example.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PokemonsBasicInfo")
data class PokemonBasicInfoDbModel(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "baseExperience") val baseExperience: Int,
)