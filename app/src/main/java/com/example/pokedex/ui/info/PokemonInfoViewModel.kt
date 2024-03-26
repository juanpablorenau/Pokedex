package com.example.pokedex.ui.info

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetPokemonInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase
) : ViewModel() {

}