package com.example.pokedex.ui.info.tabs.moves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetMoveInfoUseCase
import com.example.model.entities.MoveInfo
import com.example.model.utils.orEmptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovesUiState {
    data object Loading : MovesUiState()
    data class Error(val error: String) : MovesUiState()
    data class Success(val moves: List<MoveInfo>) : MovesUiState()
}

@HiltViewModel
class MovesViewModel @Inject constructor(
    private val getMoveInfoUseCase: GetMoveInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovesUiState>(MovesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getMovesInfo(names: List<String>) {
        viewModelScope.launch(Dispatchers.Main) {
            val deferredMoves = names.map { name ->
                async(Dispatchers.IO) {
                    getMoveInfoUseCase(name)
                        .catch { exception -> setErrorState(exception.message.orEmptyString()) }
                        .firstOrNull()
                }
            }
            val moves = deferredMoves.awaitAll().filterNotNull()
            setSuccessState(moves)
        }
    }

    private fun setSuccessState(moves: List<MoveInfo>) {
        _uiState.value = MovesUiState.Success(moves)
    }

    private fun setErrorState(error: String) {
        _uiState.value = MovesUiState.Error(error)
    }
}
