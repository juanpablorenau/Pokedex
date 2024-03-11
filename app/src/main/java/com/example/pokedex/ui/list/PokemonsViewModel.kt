package com.example.pokedex.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetPokemonsUseCase
import com.example.model.entities.Pokemon
import com.example.model.utils.orEmptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonsUiState(
    val items: List<Pokemon> = emptyList(),
    val error: String = "",
)

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonsUiState())
    val uiState: StateFlow<PokemonsUiState> = _uiState

    init {
        getPokemons()
    }

    private fun getPokemons() {
        viewModelScope.launch(Dispatchers.Main) {
            getPokemonsUseCase.invoke()
                .catch { exception -> updateError(exception.message.orEmptyString()) }
                .collect { pokemons -> updateItems(pokemons) }
        }
    }

    private fun updateItems(list: List<Pokemon>) {
        _uiState.update { it.copy(items = list) }
    }

    private fun updateError(error: String) {
        _uiState.update { it.copy(error = error) }
    }
}