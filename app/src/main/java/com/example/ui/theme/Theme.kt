package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = RosePrimaryDark,
    onPrimary = RoseOnPrimaryDark,
    primaryContainer = RosePrimaryContainerDark,
    onPrimaryContainer = RoseOnPrimaryContainerDark,
    secondary = RoseSecondary,
    onSecondary = RoseOnSecondary,
    secondaryContainer = RoseSecondaryContainer,
    onSecondaryContainer = RoseOnSecondaryContainer,
    background = RoseBackgroundDark,
    onBackground = RoseOnBackgroundDark,
    surface = RoseSurfaceDark,
    onSurface = RoseOnSurfaceDark,
    surfaceVariant = RoseSurfaceVariantDark,
    onSurfaceVariant = RoseOnSurfaceVariantDark,
    outline = RoseOutline
)

private val LightColorScheme = lightColorScheme(
    primary = RosePrimary,
    onPrimary = RoseOnPrimary,
    primaryContainer = RosePrimaryContainer,
    onPrimaryContainer = RoseOnPrimaryContainer,
    secondary = RoseSecondary,
    onSecondary = RoseOnSecondary,
    secondaryContainer = RoseSecondaryContainer,
    onSecondaryContainer = RoseOnSecondaryContainer,
    tertiary = RoseTertiary,
    tertiaryContainer = RoseTertiaryContainer,
    onTertiaryContainer = RoseOnTertiaryContainer,
    background = RoseBackground,
    onBackground = RoseOnBackground,
    surface = RoseSurface,
    onSurface = RoseOnSurface,
    surfaceVariant = RoseSurfaceVariant,
    onSurfaceVariant = RoseOnSurfaceVariant,
    outline = RoseOutline
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We intentionally keep our signature romantic pink aesthetic
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
