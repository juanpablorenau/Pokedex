package com.example.pokedex.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetPokemonInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase,
) : ViewModel() {

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPokemonInfoUseCase.invoke("bulbasaur").collect{
                    println("LLamada: $it")
                }

            } catch (e: Exception) {
                Log.e("ViewModel", e.message.toString())
            }
        }
    }
}