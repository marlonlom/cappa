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

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsSwitch
import dev.marlonlom.demos.ajv_cappa.R

@Composable
fun CatalogSettingsRoute(
  windowSizeClass: WindowSizeClass,
  settingsUiState: CatalogSetting?,
  updateBooleanSettingAction: (String, Boolean) -> Unit,
  openExternalUrlAction: (String) -> Unit,
  openLicensesSectionAction: () -> Unit,
  modifier: Modifier = Modifier
) {
  val columnPaddingHorizontal = when (windowSizeClass.widthSizeClass) {
    WindowWidthSizeClass.Compact -> 20.dp
    else -> 60.dp
  }

  when (settingsUiState) {
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
        settingsUiState = settingsUiState,
        updateBooleanSettingAction = updateBooleanSettingAction,
        openExternalUrlAction = openExternalUrlAction,
        openLicensesSectionAction = openLicensesSectionAction
      )
    }
  }

}

@Composable
fun CatalogSettingsScreen(
  windowSizeClass: WindowSizeClass,
  columnPaddingHorizontal: Dp,
  settingsUiState: CatalogSetting,
  updateBooleanSettingAction: (String, Boolean) -> Unit,
  openExternalUrlAction: (String) -> Unit,
  openLicensesSectionAction: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = columnPaddingHorizontal)
      .verticalScroll(rememberScrollState())
  ) {
    SettingsTitleText(windowSizeClass = windowSizeClass)
    SettingsSwitch(
      title = {
        Text(text = stringResource(R.string.settings_label_dark_theme))
      },
      onCheckedChange = { toggled ->
        updateBooleanSettingAction("dark_theme", toggled)
      }
    )
    SettingsSwitch(
      enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
      title = {
        Text(text = stringResource(R.string.settings_label_dynamic_colors))
      },
      onCheckedChange = { toggled ->
        updateBooleanSettingAction("dynamic_colors", toggled)
      }
    )
    Divider(modifier = Modifier.padding(bottom = 10.dp))
    ThirdPartySoftwareSettingMenuLink(openLicensesSectionAction)
    PrivacyPolicySettingMenuLink(settingsUiState, openExternalUrlAction)
    TermsConditionsSettingMenuLink(settingsUiState, openExternalUrlAction)
    PersonalDataPolicySettingMenuLink(settingsUiState, openExternalUrlAction)
    Divider(modifier = Modifier.padding(bottom = 10.dp))
    AppVersionSettingsMenuLink(settingsUiState)
  }
}

