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

package dev.marlonlom.demos.ajv_cappa.catalog.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun CatalogSettingsRoute(
  windowSizeClass: WindowSizeClass,
  routeParams: CatalogSettingsRouteParams,
  modifier: Modifier = Modifier
) {
  val columnPaddingHorizontal = when (windowSizeClass.widthSizeClass) {
    WindowWidthSizeClass.Compact -> 20.dp
    else -> 60.dp
  }
  Timber.d("[CatalogSettingsRoute] settingsUiState=${routeParams.settingsUiState}")
  when (routeParams.settingsUiState) {
    null -> {
      Text(
        text = "No Settings :( ",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
      )
    }

    else -> {
      CatalogSettingsScreen(
        modifier = modifier,
        windowSizeClass = windowSizeClass,
        columnPaddingHorizontal = columnPaddingHorizontal,
        routeParams = routeParams
      )
    }
  }
}

/**
 * Data class that represents catalog settings ui route params.
 *
 * @author marlonlom
 *
 * @property settingsUiState Catalog setting value.
 * @property updateBooleanSettingAction Action for updating boolean settings.
 * @property openExternalUrlAction Action for opening external urls.
 * @property openLicensesSectionAction Action for opening licenses content.
 */
data class CatalogSettingsRouteParams(
  val settingsUiState: CatalogSetting?,
  val updateBooleanSettingAction: (String, Boolean) -> Unit,
  val openExternalUrlAction: (String) -> Unit,
  val openLicensesSectionAction: () -> Unit
)
