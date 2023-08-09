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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.marlonlom.demos.ajv_cappa.main.data.CatalogDataService
import dev.marlonlom.demos.ajv_cappa.ui.detail.DetailRoute
import dev.marlonlom.demos.ajv_cappa.ui.detail.DetailViewModel
import dev.marlonlom.demos.ajv_cappa.ui.home.HomeRoute
import dev.marlonlom.demos.ajv_cappa.ui.home.HomeViewModel
import dev.marlonlom.demos.ajv_cappa.ui.settings.SettingsRoute
import timber.log.Timber

@Composable
fun NavigationHost(
  navController: NavHostController,
  paddingValues: PaddingValues,
  windowSizeClass: WindowSizeClass,
  navigateToProductDetailRoute: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val isExpandedWidth = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

  NavHost(
    navController = navController,
    startDestination = Destination.Home.route,
  ) {
    composable(Destination.Home.route) {
      val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.provideFactory(
          CatalogDataService()
        )
      )
      HomeRoute(
        paddingValues = paddingValues,
        windowSizeClass = windowSizeClass,
        viewModel = homeViewModel,
        modifier = modifier,
        navigateToProductDetailRoute = navigateToProductDetailRoute
      )
    }

    if (!isExpandedWidth) {
      composable(
        route = Destination.Detail.routeWithArg,
        arguments = Destination.Detail.arguments
      ) { navBackStackEntry ->
        val catalogItemId = navBackStackEntry.arguments!!.getLong(Destination.Detail.itemIdArg)
        Timber.d("[NavigationHost] catalogItemId=$catalogItemId")
        val detailViewModel: DetailViewModel = viewModel(
          factory = DetailViewModel.provideFactory(
            dataService = CatalogDataService()
          )
        )

        DetailRoute(
          catalogItemId = catalogItemId,
          paddingValues = paddingValues,
          windowSizeClass = windowSizeClass,
          viewModel = detailViewModel
        )
      }
    }

    composable(Destination.Settings.route) {
      SettingsRoute(
        paddingValues = paddingValues
      )
    }
  }
}
