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

package dev.marlonlom.demos.ajv_cappa.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogItem
import dev.marlonlom.demos.ajv_cappa.ui.common.CatalogUiState
import dev.marlonlom.demos.ajv_cappa.ui.home.HomeRoute
import dev.marlonlom.demos.ajv_cappa.ui.navigation.Destination.Companion.detailArguments
import dev.marlonlom.demos.ajv_cappa.ui.settings.SettingsRoute

@Composable
fun NavigationHost(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  windowSizeClass: WindowSizeClass,
  uiState: CatalogUiState,
  selectedItem: CatalogItem?,
  navigateToProductDetailRoute: (Long) -> Unit
) {

  NavHost(
    navController = navController,
    startDestination = Destination.Home.route,
  ) {
    composable(
      route = Destination.routeWithDetail(Destination.Home.route),
      arguments = detailArguments
    ) {

      HomeRoute(
        windowSizeClass = windowSizeClass,
        modifier = modifier,
        uiState = uiState,
        selectedItem = selectedItem,
        navigateToProductDetailRoute = navigateToProductDetailRoute
      )
    }

    composable(Destination.Settings.route) {
      SettingsRoute(
        modifier = modifier
      )
    }
  }
}
