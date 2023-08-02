package dev.marlonlom.demos.ajv_cappa.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val brandDarkColorScheme = darkColorScheme(
  primary = PapayaWhip80,
  onPrimary = Color.White,
  secondary = Melon80,
  onSecondary = Color.White,
  tertiary = MonaLisa80,
  onTertiary = Color.White,
  background = Bone80,
  onBackground = Color.White,
  surface = Bone80,
  onSurface = Color.White
)

private val brandLightColorScheme = lightColorScheme(
  primary = PapayaWhip40,
  onPrimary = Color.Black,
  secondary = Melon40,
  onSecondary = Color.Black,
  tertiary = MonaLisa40,
  background = Bone40,
  onBackground = BlackRussian40,
  surface = Bone40,
  onSurface = BlackRussian40

  /* Other default colors to override
  background = Color(0xFFFFFBFE),
  surface = Color(0xFFFFFBFE),
  onPrimary = Color.White,
  onSecondary = Color.White,
  onTertiary = Color.White,
  onBackground = Color(0xFF1C1B1F),
  onSurface = Color(0xFF1C1B1F),
  */
)

@Composable
fun CappaTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    darkTheme -> brandDarkColorScheme
    else -> brandLightColorScheme
  }

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = CappaTypography,
    shapes = CappaShapes,
    content = content
  )
}
