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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CatalogSettingsScreen(
  windowSizeClass: WindowSizeClass,
  columnPaddingHorizontal: Dp,
  routeParams: CatalogSettingsRouteParams,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = columnPaddingHorizontal)
      .verticalScroll(rememberScrollState())
  ) {
    SettingsTitleText(windowSizeClass = windowSizeClass)
    DarkThemeSettingSwitch(routeParams)
    DynamicColorsSettingSwitch(routeParams)
    Divider(modifier = Modifier.padding(bottom = 10.dp))
    ThirdPartySoftwareSettingMenuLink(routeParams)
    PrivacyPolicySettingMenuLink(routeParams)
    TermsConditionsSettingMenuLink(routeParams)
    PersonalDataPolicySettingMenuLink(routeParams)
    Divider(modifier = Modifier.padding(bottom = 10.dp))
    AppVersionSettingsMenuLink(routeParams)
  }
}

