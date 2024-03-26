package com.example.pokedex.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetPokemonInfoUseCase
import com.example.model.entities.PokemonInfo
import com.example.model.utils.orEmptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PokemonInfoUiState {
    data object Loading : PokemonInfoUiState()
    data class Success(val pokemonInfo: PokemonInfo) : PokemonInfoUiState()
    data class Error(val error: String) : PokemonInfoUiState()
}

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonInfoUiState>(PokemonInfoUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPokemonInfo(name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            getPokemonInfoUseCase(name)
                .catch { exception -> setErrorState(exception.message.orEmptyString()) }
                .collect { pokemonInfo -> setSuccessState(pokemonInfo) }
        }
    }

    private fun setSuccessState(pokemonInfo: PokemonInfo) {
        _uiState.value = PokemonInfoUiState.Success(pokemonInfo)
    }

    private fun setErrorState(error: String) {
        _uiState.value = PokemonInfoUiState.Error(error)
    }
}