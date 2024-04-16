package com.example.pokedex.ui.info.tabs.stats

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    color: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 12.dp, end = 12.dp)
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

                ShowProgress(stat.baseStat, color)
            }
        }
    }
}

@Composable
private  fun ShowProgress(score: Int = 100, color: Int) {
    val gradient = Brush.linearGradient(listOf(Color(color), Color.Gray))
    val progressFactor = remember { Animatable(0f) }

    LaunchedEffect(score) {
        progressFactor.animateTo(
            targetValue = score * 0.0075f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

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
                .fillMaxWidth(progressFactor.value)
                .background(brush = gradient),
            text = "",
            color = Color.White
        )
    }
}
