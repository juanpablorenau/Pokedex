package com.example.pokedex.ui.info.tabs.about

import android.graphics.Color.BLACK
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.entities.Characteristics
import com.example.model.entities.PokemonInfo
import com.example.pokedex.components.CircularProgress
import com.example.pokedex.utils.getViewModel

@Composable
fun AboutTab(
    paddingValues: PaddingValues,
    pokemonInfo: PokemonInfo,
    dominantColor: Int,
) {
    val viewModel = LocalContext.current.getViewModel<AboutViewModel>()
    val uiState: AboutUiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getCharacteristics(pokemonInfo.id)

    when (val state = uiState) {
        is AboutUiState.Loading -> {}
        is AboutUiState.Success -> SuccessScreen(
            paddingValues = paddingValues,
            pokemonInfo = pokemonInfo,
            dominantColor = dominantColor,
            characteristics = state.characteristics
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreen(
    paddingValues: PaddingValues = PaddingValues(),
    pokemonInfo: PokemonInfo = PokemonInfo(),
    dominantColor: Int = BLACK,
    characteristics: Characteristics = Characteristics(description = "Hola"),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding() + 24.dp, start = 12.dp, end = 12.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Description",
            color = Color(dominantColor),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = characteristics.description,
            color = Color(BLACK),
            fontSize = 20.sp,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Experience",
                    color = Color(dominantColor),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                CircularProgress(
                    progress = pokemonInfo.baseExperience,
                    total = 340f,
                    color = dominantColor,
                )
            }

            Column(
                modifier = Modifier.weight(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Height",
                    color = Color(dominantColor),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                CircularProgress(
                    progress = pokemonInfo.height,
                    total = 70f,
                    text = " m",
                    color = dominantColor,
                )
            }

            Column(
                modifier = Modifier.weight(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Weight",
                    color = Color(dominantColor),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                CircularProgress(
                    progress = pokemonInfo.weight,
                    total = 1200f,
                    text = " Kg",
                    color = dominantColor,
                )
            }
        }


    }
}