package com.example.data.model.api

import com.example.model.entities.MoveInfo
import com.example.model.entities.enums.PokemonType
import com.google.gson.annotations.SerializedName

data class MoveApiModel(
    @SerializedName("move") val move: MoveInfoApiModel,
)

data class MoveInfoApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("accuracy") val accuracy: Int,
    @SerializedName("power") val power: Int,
    @SerializedName("pp") val pp: Int,
    @SerializedName("type") val type: TypeInfoApiModel,
    @SerializedName("effect_entries") val effects: List<EffectApiModel>,
) {
    fun getMoveType() =
        try {
            PokemonType.valueOf(type.name.uppercase())
        } catch (e: IllegalArgumentException) {
            PokemonType.UNKNOWN
        }

    fun getMoveEffects() =
        effects.filter { it.languageApiModel.name == "EN" }.map { it.effect }
}

data class EffectApiModel(
    @SerializedName("effect") val effect: String,
    @SerializedName("language") val languageApiModel: LanguageApiModel,
)

fun MoveInfoApiModel.toDomainModel() = MoveInfo(
    id = id,
    name = name,
    accuracy = accuracy,
    power = power,
    pp = pp,
    type = getMoveType(),
    effects = getMoveEffects()
)