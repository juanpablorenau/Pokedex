package com.example.pokedex.ui.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.request.SuccessResult
import com.example.domain.usecase.GetPokemonUseCase
import com.example.domain.usecase.GetPokemonsUseCase
import com.example.model.entities.Pokemon
import com.example.model.utils.orEmptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PokemonsUiState {
    data object Loading : PokemonsUiState()
    data class Success(val pokemons: List<Pokemon>) : PokemonsUiState()
    data class Error(val error: String) : PokemonsUiState()
}

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    private val getPokemonUseCase: GetPokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonsUiState>(PokemonsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getPokemons()
    }

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.Main) {
            getPokemonsUseCase()
                .catch { exception -> setErrorState(exception.message.orEmptyString()) }
                .collect { pokemons -> setSuccessState(pokemons) }
        }
    }

    fun getPokemon(name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            getPokemonUseCase(name)
                .catch { exception -> setErrorState(exception.message.orEmptyString()) }
                .collect { pokemon -> setSuccessState(listOf(pokemon)) }
        }
    }

    private fun setSuccessState(list: List<Pokemon>) {
        _uiState.value = PokemonsUiState.Success(list)
    }

    fun setErrorState(error: String) {
        _uiState.value = PokemonsUiState.Error(error)
    }

    fun getDominantColor(result: SuccessResult): Int {
        val bitmap = (result.drawable as BitmapDrawable).bitmap
        val convertedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)
        val palette = Palette.from(convertedBitmap).generate()
        return palette.getDominantColor(Color.Black.value.toInt())
    }
}