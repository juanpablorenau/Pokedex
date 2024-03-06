package com.example.pokedex.ui.list

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetPokemonInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase
) : ViewModel() {

    fun init() {
        val x = getPokemonInfoUseCase.invoke("bulbasaur")
        println("LLamada${x}")
    }
}