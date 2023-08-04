/*
 * Copyright (c) 2023 Marlonlom
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val brandDarkColorScheme = darkColorScheme(
  primary = BrandColors.macaroniAndCheese,
  onPrimary = BrandColors.maroon5,
  primaryContainer = BrandColors.maroon7,
  onPrimaryContainer = BrandColors.bisque,
  secondary = BrandColors.lightPink,
  onSecondary = BrandColors.pohutukawa,
  secondaryContainer = BrandColors.paprika,
  onSecondaryContainer = BrandColors.mistyRose3,
  tertiary = BrandColors.melon,
  onTertiary = BrandColors.faluRed,
  tertiaryContainer = BrandColors.flameRed,
  onTertiaryContainer = BrandColors.mistyRose2,
  error = BrandColors.melon2,
  errorContainer = BrandColors.sangria,
  onError = BrandColors.maroon6,
  onErrorContainer = BrandColors.mistyRose,
  background = BrandColors.maroon,
  onBackground = BrandColors.peachPuff,
  surface = BrandColors.maroon,
  onSurface = BrandColors.peachPuff,
  surfaceVariant = BrandColors.paco,
  onSurfaceVariant = BrandColors.wafer,
  outline = BrandColors.zorba,
  inverseOnSurface = BrandColors.maroon,
  inverseSurface = BrandColors.peachPuff,
  inversePrimary = BrandColors.olive,
  surfaceTint = BrandColors.macaroniAndCheese,
  outlineVariant = BrandColors.paco,
  scrim = BrandColors.black,

  /*primary = PapayaWhip80,
  onPrimary = Color.White,
  secondary = Melon80,
  onSecondary = Color.White,
  tertiary = MonaLisa80,
  onTertiary = Color.White,
  background = Bone80,
  onBackground = Color.White,
  surface = Bone80,
  onSurface = Color.White*/
)

private val brandLightColorScheme = lightColorScheme(
  primary = BrandColors.olive,
  onPrimary = BrandColors.white,
  primaryContainer = BrandColors.bisque,
  onPrimaryContainer = BrandColors.maroon2,
  secondary = BrandColors.roofTerracotta,
  onSecondary = BrandColors.white,
  secondaryContainer = BrandColors.mistyRose3,
  onSecondaryContainer = BrandColors.tyrianPurple,
  tertiary = BrandColors.mexicanRed,
  onTertiary = BrandColors.white,
  tertiaryContainer = BrandColors.mistyRose2,
  onTertiaryContainer = BrandColors.tyrianPurple2,
  error = BrandColors.fireBrick,
  errorContainer = BrandColors.mistyRose,
  onError = BrandColors.white,
  onErrorContainer = BrandColors.maroon3,
  background = BrandColors.lavenderBlush,
  onBackground = BrandColors.maroon,
  surface = BrandColors.lavenderBlush,
  onSurface = BrandColors.maroon,
  surfaceVariant = BrandColors.provincialPink,
  onSurfaceVariant = BrandColors.paco,
  outline = BrandColors.sandDune,
  inverseOnSurface = BrandColors.seashell,
  inverseSurface = BrandColors.maroon4,
  inversePrimary = BrandColors.macaroniAndCheese,
  surfaceTint = BrandColors.olive,
  outlineVariant = BrandColors.wafer,
  scrim = BrandColors.black

  /*primary = PapayaWhip40,
    onPrimary = Color.Black,
    primaryContainer = PapayaWhip40,
    onPrimaryContainer = Melon10,
    secondary = Melon40,
    onSecondary = Color.Black,
    secondaryContainer = Melon90,
    onSecondaryContainer = Melon10,
    tertiary = MonaLisa40,
    onTertiary = BlackRussian40,
    tertiaryContainer = MonaLisa90,
    onTertiaryContainer = MonaLisa10,
    background = Bone40,
    onBackground = BlackRussian40,
    surface = Bone40,
    onSurface = BlackRussian40,
    surfaceVariant = Bone80,
    onSurfaceVariant = Color.White*/

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
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = CappaTypography,
    shapes = CappaShapes,
    content = content
  )
}
