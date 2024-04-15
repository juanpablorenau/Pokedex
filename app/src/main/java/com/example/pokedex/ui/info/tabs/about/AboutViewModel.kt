package com.example.pokedex.ui.info.tabs.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetPokemonCharacteristics
import com.example.model.entities.Characteristics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AboutUiState {
    data object Loading : AboutUiState()
    data class Success(val characteristics: Characteristics) : AboutUiState()
}

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getPokemonCharacteristics: GetPokemonCharacteristics,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AboutUiState>(AboutUiState.Loading)
    val uiState = _uiState.asStateFlow()

    companion object {
        val description =
            "We do not have information about this pokemon since no trainer has been able to capture it"
    }

    fun getCharacteristics(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            getPokemonCharacteristics(id)
                .catch {
                    setSuccessState(Characteristics(id = id, description = description))
                }
                .collect { characteristics -> setSuccessState(characteristics) }
        }
    }

    private fun setSuccessState(characteristics: Characteristics) {
        _uiState.value = AboutUiState.Success(characteristics)
    }
}