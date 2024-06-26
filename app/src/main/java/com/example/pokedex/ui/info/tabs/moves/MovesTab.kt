package com.example.pokedex.ui.info.tabs.moves

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

    LaunchedEffect(Unit) {
        viewModel.setLoadingState()
        viewModel.getMovesInfo(pokemonInfo.moves)
    }

    when (val state = uiState) {
        is MovesUiState.Loading -> LoadingScreen(color)
        is MovesUiState.Error -> ErrorScreen()
        is MovesUiState.Success -> {
            SuccessScreen(
                paddingValues = paddingValues,
                color = color,
                moves = state.moves
            )
        }
    }
}

@Composable
private fun LoadingScreen(color: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,

        ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = Color(color),
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
    color: Int,
    moves: List<MoveInfo>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        items(moves.sortedBy { it.name }) { move ->
            ItemMove(move, color)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemMove(move: MoveInfo = MoveInfo(), color: Int = Color.LightGray.toArgb()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.58f),
                color = Color(move.type.color),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                text = move.name.visualFormat("-")
            )

            Text(
                modifier = Modifier
                    .weight(0.2f),
                text = move.power.toString(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(move.type.color)
            )

            Card(
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .wrapContentSize(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors().copy(containerColor = Color(move.type.color)),
            ) {
                val context = LocalContext.current
                val resourceId = context.resources.getIdentifier(
                    move.type.name.lowercase(), "drawable", context.packageName
                )

                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp),
                    painter = painterResource(id = resourceId),
                    contentDescription = "typeImage"
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp), color = Color(color)
        )
    }
}
