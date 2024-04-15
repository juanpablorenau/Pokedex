package com.example.pokedex.ui.info.tabs.moves

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.entities.MoveInfo
import com.example.model.entities.PokemonInfo
import com.example.model.utils.visualFormat
import com.example.pokedex.utils.getViewModel

@Composable
fun MovesTab(
    paddingValues: PaddingValues,
    pokemonInfo: PokemonInfo,
    color: Int,
) {
    val viewModel = LocalContext.current.getViewModel<MovesViewModel>()
    val uiState: MovesUiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getMovesInfo(pokemonInfo.moves)

    when (val state = uiState) {
        is MovesUiState.Loading -> LoadingScreen()
        is MovesUiState.Error -> ErrorScreen()
        is MovesUiState.Success -> {
            SuccessScreen(
                paddingValues = paddingValues,
                pokemonInfo = pokemonInfo,
                color = color,
                moves = state.moves
            )
        }
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
private fun ErrorScreen() {
}

@Composable
private fun SuccessScreen(
    paddingValues: PaddingValues,
    pokemonInfo: PokemonInfo,
    color: Int,
    moves: List<MoveInfo>,
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(moves.sortedBy { it.name }) { move ->
            ItemMove(move, color)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemMove(move: MoveInfo = MoveInfo(), color: Int = Color.Red.toArgb()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            text = move.name.visualFormat("-")
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = Color(color)
        )
    }
}