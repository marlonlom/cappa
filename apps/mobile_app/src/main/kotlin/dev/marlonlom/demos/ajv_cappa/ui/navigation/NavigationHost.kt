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
import dev.marlonlom.demos.ajv_cappa.catalog.detail.CatalogDetail
import dev.marlonlom.demos.ajv_cappa.catalog.detail.CatalogDetailRoute
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogListRoute
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogListState
import dev.marlonlom.demos.ajv_cappa.ui.settings.SettingsRoute
import timber.log.Timber

@Composable
fun NavigationHost(
  navController: NavHostController,
  windowSizeClass: WindowSizeClass,
  listUiState: CatalogListState,
  detailUiState: CatalogDetail?,
  onBackPressed: () -> Unit,
  gotoDetailRoute: (Long) -> Unit,
  findSingleItem: (Long) -> Unit,
  modifier: Modifier = Modifier
) {

  NavHost(
    navController = navController,
    startDestination = Destination.CatalogList.route,
  ) {
    composable(
      route = Destination.CatalogList.route
    ) {
      CatalogListRoute(
        windowSizeClass = windowSizeClass,
        modifier = modifier,
        listUiState = listUiState,
        detailUiState = detailUiState,
        findSingleItem = findSingleItem,
        gotoDetailRoute = gotoDetailRoute
      )
    }

    composable(Destination.Settings.route) {
      SettingsRoute(
        windowSizeClass = windowSizeClass,
        modifier = modifier
      )
    }

    composable(
      route = Destination.Detail.route,
      arguments = Destination.Detail.arguments
    ) { backStackEntry ->
      val itemId = backStackEntry.arguments!!.getLong(Destination.itemIdArg)
      findSingleItem(itemId)
      Timber.d("[NavigationHost>CatalogDetailRoute] itemId=$itemId; detailState=${detailUiState}")
      CatalogDetailRoute(
        onBackPressed = onBackPressed,
        windowSizeClass = windowSizeClass,
        catalogItem = detailUiState
      )
    }
  }
}
