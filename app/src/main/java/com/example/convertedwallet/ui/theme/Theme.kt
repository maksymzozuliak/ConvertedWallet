package com.example.convertedwallet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Blue,
    secondary = LightBlue,
    background = Color.White,
    primaryVariant = Color.White,
    secondaryVariant = Color.LightGray,

)

@Composable
fun ConvertedWalletTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}