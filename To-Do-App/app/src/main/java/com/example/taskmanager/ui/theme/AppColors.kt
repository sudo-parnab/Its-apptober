package com.example.taskmanager.ui.theme
import androidx.compose.material.lightColors
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

object AppColors {
    val lightColors = lightColors(
        primary = Color(0xFF00BCD4), // Your primary color for light theme
        secondary = Color(0xFF009688), // Your secondary color for light theme
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color.Black,
        onSurface = Color.Black,
    )
    val darkColors = darkColors(
        primary = Color(0xFF007FFF), // Your primary color for dark theme
        secondary = Color(0xFF81C78D), // Your secondary color for dark theme
        background = Color.Black,
        surface = Color(0xFF121212),
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White,
    )
}
