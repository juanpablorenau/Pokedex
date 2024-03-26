package com.example.pokedex.ui.info

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.entities.PokemonInfo
import com.example.pokedex.R
import com.example.pokedex.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonInfoActivity : AppCompatActivity() {

    private val viewModel: PokemonInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PokedexTheme { PokemonInfoScreen() } }
    }

    @Composable
    private fun PokemonInfoScreen() {
        val uiState: PokemonInfoUiState by viewModel.uiState.collectAsStateWithLifecycle()

        when (val state = uiState) {
            is PokemonInfoUiState.Loading -> LoadingScreen()
            is PokemonInfoUiState.Success -> SuccessScreen(state.pokemonInfo)
            is PokemonInfoUiState.Error -> ErrorScreen(state.error)
        }
    }

    @Composable
    private fun LoadingScreen() {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }

    @Composable
    private fun ErrorScreen(error: String = "") {
        AlertDialog(onDismissRequest = { },
            title = { Text("Ha ocurrido un error") },
            text = { Text(error) },
            confirmButton = {
                Text(
                    modifier = Modifier.clickable { },
                    text = "Aceptar",
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "Error Icon"
                )
            })
    }

    @Preview(showBackground = true)
    @Composable
    fun SuccessScreen(pokemonInfo: PokemonInfo = PokemonInfo()) {

    }
}