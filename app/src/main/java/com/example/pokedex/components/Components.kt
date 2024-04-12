package com.example.pokedex.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun CircularProgress(
    progress: Int = 25,
    total: Float = 100f,
    text: String = "",
    color: Int = Color.Blue.toArgb(),
    size: Dp = 90.dp,
    stroke: Dp = 8.dp,
) {
    val progressSweepAngle = remember { Animatable(0f) }
    val progressText = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        progressSweepAngle.animateTo(
            targetValue = progress.div(total),
            animationSpec = tween(durationMillis = 1000)
        )
    }

    LaunchedEffect(progress) {
        progressText.animateTo(
            targetValue = progress.toFloat(),
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Box(modifier = Modifier.size(size)) {
        Canvas(modifier = Modifier.size(size)) {
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(stroke.toPx())
            )
            drawArc(
                color = Color(color),
                startAngle = -90f,
                sweepAngle = progressSweepAngle.value * 360f,
                useCenter = false,
                style = Stroke(stroke.toPx())
            )
        }

        Text(
            text = "${Math.round(progressText.value)}$text",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}