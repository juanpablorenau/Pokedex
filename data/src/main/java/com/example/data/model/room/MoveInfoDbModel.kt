package com.example.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.entities.MoveInfo
import com.example.model.entities.enums.PokemonType

@Entity(tableName = "MovesInfo")
data class MoveInfoDbModel(
    @PrimaryKey val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("accuracy") val accuracy: Int,
    @ColumnInfo("power") val power: Int,
    @ColumnInfo("pp") val pp: Int,
    @ColumnInfo("type") val type: PokemonType,
)

fun MoveInfoDbModel.toDomainModel() = MoveInfo(
    id = id,
    name = name,
    accuracy = accuracy,
    power = power,
    pp = pp,
    type = type
)

fun MoveInfo.toDbModel() = MoveInfoDbModel(
    id = id,
    name = name,
    accuracy = accuracy,
    power = power,
    pp = pp,
    type = type
)