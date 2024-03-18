package com.example.pokedex.ui.list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.model.entities.Pokemon
import com.example.pokedex.R
import com.example.pokedex.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsActivity : AppCompatActivity() {

    private val viewModel: PokemonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PokedexTheme { PokemonListScreen() } }
    }

    @Preview(showBackground = true)
    @Composable
    private fun PokemonListScreen() {
        val uiState: PokemonsUiState by viewModel.uiState.collectAsStateWithLifecycle()

        when (val state = uiState) {
            is PokemonsUiState.Loading -> LoadingScreen()
            is PokemonsUiState.Success -> SuccessScreen(state.pokemons)
            is PokemonsUiState.Error -> ErrorScreen(state.error)
        }
    }

    @Composable
    private fun LoadingScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }

    @Composable
    private fun ErrorScreen(error: String) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Ha ocurrido un error") },
            text = { Text(error) },
            confirmButton = { Text("Aceptar") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "Error Icon"
                )
            }
        )
    }

    @Composable
    fun SuccessScreen(pokemons: List<Pokemon>) {
        LazyColumn {
            items(pokemons) { pokemon -> ItemPokemon(pokemon) }
        }
    }

    @Composable
    fun ItemPokemon(pokemon: Pokemon) {
        Card(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(pokemon.imageColor)),

        ) {
            AsyncImage(
                model = pokemon.url,
                contentDescription = "Pokemon image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                onError = { error -> viewModel.setErrorState(error.result.toString()) },
                onSuccess = { result -> viewModel.updatePokemonColor(pokemon, result.result) }
            )
            Text(
                text = pokemon.name,
                fontSize = 42.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
