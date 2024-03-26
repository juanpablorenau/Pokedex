package com.example.pokedex.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import coil.request.SuccessResult

fun getDominantColor(result: SuccessResult): Int {
    val bitmap = (result.drawable as BitmapDrawable).bitmap
    val convertedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)
    val palette = Palette.from(convertedBitmap).generate()
    return palette.getDominantColor(Color.Black.value.toInt())
}