package com.example.pokedex.ui.info.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.model.entities.PokemonInfo

@Composable
fun StatsTab(
    paddingValues: PaddingValues,
    pokemonInfo: PokemonInfo,
    dominantColor: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 8.dp, end = 8.dp)
    ) {
        pokemonInfo.stats.forEach { stat ->
            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.25f),
                    text = stat.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.fillMaxWidth(0.2f),
                    text = stat.baseStat.toString(),
                    textAlign = TextAlign.Center,
                )

                ShowProgress(stat.baseStat, dominantColor)
            }
        }
    }
}

@Composable
fun ShowProgress(score: Int = 100, dominantColor: Int) {

    val gradient = Brush.linearGradient(listOf(Color(dominantColor), Color.Gray))
    val progressFactor by remember { mutableFloatStateOf( score * 0.0075f) }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(12.dp)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(colors = listOf(Color.Gray, Color.Gray)),
                shape = RoundedCornerShape(50.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(brush = gradient),
            text = "",
            color = Color.Transparent
        )
    }
}
