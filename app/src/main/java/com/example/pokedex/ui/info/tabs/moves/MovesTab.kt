package com.example.pokedex.ui.info.tabs.moves

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.model.entities.PokemonInfo
import com.example.model.utils.visualFormat

@Composable
fun MovesTab(
    paddingValues: PaddingValues,
    pokemonInfo: PokemonInfo,
    color: Int,
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(pokemonInfo.moves.sorted()) { move ->
            ItemMove(move, color)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemMove(move: String = "Attack", color: Int = Color.Red.toArgb()) {
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
            text = move.visualFormat("-")
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = Color(color)
        )
    }
}