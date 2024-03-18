package com.example.pokedex.ui.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.example.model.entities.Pokemon
import com.example.pokedex.theme.PokedexTheme
import com.example.pokedex.theme.Purple40
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
    }

    @Composable
    private fun ErrorScreen(error: String) {
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
                onSuccess = { result ->
                    val bitmap = (result.result.drawable as BitmapDrawable).bitmap
                    val convertedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)
                    val palette = Palette.from(convertedBitmap).generate()
                    val dominantColor = palette.getDominantColor(Color.Red.value.toInt())
                    pokemon.imageColor = dominantColor
                })
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
