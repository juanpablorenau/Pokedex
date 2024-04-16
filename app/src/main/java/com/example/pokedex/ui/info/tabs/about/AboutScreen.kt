package com.example.pokedex.ui.info.tabs.about

import android.graphics.Color.BLACK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.entities.Characteristics
import com.example.model.entities.PokemonInfo
import com.example.model.utils.addQuotationMarks
import com.example.pokedex.components.CircularProgress
import com.example.pokedex.utils.getViewModel

@Composable
fun AboutTab(
    paddingValues: PaddingValues,
    pokemonInfo: PokemonInfo,
    color: Int,
) {
    val viewModel = LocalContext.current.getViewModel<AboutViewModel>()
    val uiState: AboutUiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getCharacteristics(pokemonInfo.id)

    when (val state = uiState) {
        is AboutUiState.Loading -> {}
        is AboutUiState.Success -> SuccessScreen(
            paddingValues = paddingValues,
            pokemonInfo = pokemonInfo,
            color = color,
            characteristics = state.characteristics
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessScreen(
    paddingValues: PaddingValues = PaddingValues(),
    pokemonInfo: PokemonInfo = PokemonInfo(),
    color: Int = BLACK,
    characteristics: Characteristics = Characteristics(description = "Hola"),
) {
    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .background(Color.White),
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .weight(0.33f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = characteristics.description.addQuotationMarks(),
            color = Color(BLACK),
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive,
        )

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.66f)
        ) {
            Column(
                modifier = Modifier.weight(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Base Exp.",
                    color = Color(BLACK),
                    fontSize = 16.sp,
                )
                CircularProgress(
                    progress = pokemonInfo.baseExperience,
                    total = 340f,
                    color = color,
                )
            }

            Column(
                modifier = Modifier.weight(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Height",
                    color = Color(BLACK),
                    fontSize = 16.sp,
                )
                CircularProgress(
                    progress = pokemonInfo.height,
                    total = 70f,
                    text = " m",
                    color = color,
                )
            }

            Column(
                modifier = Modifier.weight(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "Weight",
                    color = Color(BLACK),
                    fontSize = 16.sp,
                )
                CircularProgress(
                    progress = pokemonInfo.weight,
                    total = 1200f,
                    text = " Kg",
                    color = color,
                )
            }
        }
    }
}