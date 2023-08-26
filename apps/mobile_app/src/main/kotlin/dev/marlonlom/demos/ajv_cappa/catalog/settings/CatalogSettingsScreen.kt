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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Divider
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.marlonlom.demos.ajv_cappa.ui.main.AppScaffoldUtil
import timber.log.Timber

@Composable
fun CatalogSettingsScreen(
  windowSizeClass: WindowSizeClass,
  columnPaddingHorizontal: Dp,
  routeParams: CatalogSettingsRouteParams,
  modifier: Modifier = Modifier
) {
  val gridColumnsCount = when {
    AppScaffoldUtil.isTabletLandscape(windowSizeClass) -> 6
    AppScaffoldUtil.isMobileLandscape(windowSizeClass) -> 4
    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> 3
    else -> 2
  }

  val span: (LazyGridItemSpanScope.() -> GridItemSpan) =
    {
      val currentLineSpan = when {
        AppScaffoldUtil.isMobileLandscape(windowSizeClass) -> 2
        AppScaffoldUtil.isTabletLandscape(windowSizeClass) -> 2
        else -> maxCurrentLineSpan
      }
      GridItemSpan(
        currentLineSpan
      )
    }

  Column(
    modifier = (
      if (AppScaffoldUtil.isTabletLandscape(windowSizeClass))
        Modifier.fillMaxWidth()
      else
        modifier
      ).padding(horizontal = columnPaddingHorizontal),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    SettingsTitleText(windowSizeClass = windowSizeClass)
    LazyVerticalGrid(
      columns = GridCells.Fixed(gridColumnsCount),
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      item(span = span) {
        Timber.d("$this")
        DarkThemeSettingSwitch(routeParams)
      }
      item(span = span) {
        DynamicColorsSettingSwitch(routeParams)
      }
      item(span = { GridItemSpan(maxLineSpan) }) {
        Divider(modifier = Modifier.padding(bottom = 10.dp))
      }
      item(span = span) {
        ThirdPartySoftwareSettingMenuLink(routeParams)
      }
      item(span = span) {
        PrivacyPolicySettingMenuLink(routeParams)
      }
      item(span = span) {
        TermsConditionsSettingMenuLink(routeParams)
      }
      item(span = span) {
        PersonalDataPolicySettingMenuLink(routeParams)
      }
      item(span = { GridItemSpan(maxLineSpan) }) {
        Divider(modifier = Modifier.padding(bottom = 10.dp))
      }
      item(span = span) {
        AppVersionSettingsMenuLink(routeParams)
      }
    }
  }
}

