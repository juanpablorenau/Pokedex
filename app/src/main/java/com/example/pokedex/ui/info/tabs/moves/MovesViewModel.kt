package com.example.pokedex.ui.info.tabs.moves

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class MovesUiState {
    data object Loading : MovesUiState()
    data class Success(val moves: List<String>) : MovesUiState()
}

@HiltViewModel
class MovesViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<MovesUiState>(MovesUiState.Success(listOf("Pay Day", "Mega punch")))
    val uiState = _uiState.asStateFlow()

}