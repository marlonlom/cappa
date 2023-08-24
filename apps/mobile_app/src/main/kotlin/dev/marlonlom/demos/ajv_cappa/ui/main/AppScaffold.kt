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

package dev.marlonlom.demos.ajv_cappa.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.marlonlom.demos.ajv_cappa.catalog.detail.CatalogDetailViewModel
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogListState
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogListViewModel
import dev.marlonlom.demos.ajv_cappa.catalog.search.CatalogSearchViewModel
import dev.marlonlom.demos.ajv_cappa.ui.navigation.AppNavigationActions
import dev.marlonlom.demos.ajv_cappa.ui.navigation.Destination
import dev.marlonlom.demos.ajv_cappa.ui.navigation.NavigationHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Application main scaffold screen composable function.
 *
 * @author marlonlom
 *
 * @param windowSizeClass Window size class.
 * @param modifier Modifier for the layout.
 * @param catalogListViewModel Reference for catalog list view model.
 * @param catalogDetailViewModel Reference for catalog detail view model.
 * @param catalogSearchViewModel Reference for catalog search view model.
 */
@Composable
fun MainScaffold(
  windowSizeClass: WindowSizeClass,
  modifier: Modifier = Modifier,
  catalogListViewModel: CatalogListViewModel,
  catalogDetailViewModel: CatalogDetailViewModel,
  catalogSearchViewModel: CatalogSearchViewModel
) {
  val coroutineScope = rememberCoroutineScope()
  val navController: NavHostController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute =
    navBackStackEntry?.destination?.route ?: Destination.CatalogList.route
  val navigationActions = AppNavigationActions(navController)
  val isDetailDestination = !Destination.listOf().map { it.route }.contains(currentRoute)
  val catalogListState = catalogListViewModel.uiState.collectAsStateWithLifecycle()
  val catalogSearchState = catalogSearchViewModel.uiState.collectAsStateWithLifecycle()

  val onNavigationIconClicked: () -> Unit = {
    navController.popBackStack()
  }

  retrieveDefaultSelectedCatalogDetail(
    currentRoute = currentRoute,
    windowSizeClass = windowSizeClass,
    catalogListState = catalogListState.value,
    coroutineScope = coroutineScope,
    detailViewModel = catalogDetailViewModel
  )

  if (AppScaffoldUtil.isTabletLandscape(windowSizeClass) && Destination.Detail.route == currentRoute) {
    navController.popBackStack(currentRoute, true)
    navController.navigate(Destination.CatalogList.route)
  }

  Scaffold(
    modifier = modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.surface),
    topBar = {
      if (AppScaffoldUtil.canShowTopBar(windowSizeClass, isDetailDestination)) {
        AppTopBar(
          onNavigationIconClicked = onNavigationIconClicked
        )
      }
    },
    content = { paddingValues ->
      Row {
        if (AppScaffoldUtil.canShowNavigationRail(windowSizeClass, isDetailDestination)) {
          AppNavRail(
            currentRoute = currentRoute,
            navigationActions = navigationActions
          )
        }

        val hostModifier =
          if (AppScaffoldUtil.isTabletLandscape(windowSizeClass))
            Modifier.width(334.dp)
          else Modifier.fillMaxWidth()

        NavigationHost(
          navController = navController,
          windowSizeClass = windowSizeClass,
          listUiState = catalogListState.value,
          detailUiState = catalogDetailViewModel.detail.value,
          searchState = catalogSearchState.value,
          onBackPressed = onNavigationIconClicked,
          gotoDetailRoute = { catalogItemId ->
            val detailRoutePath = Destination.Detail.createRoute(catalogItemId)
            Timber.d("[MainScaffold] routeWithDetail=$detailRoutePath")
            navController.navigate(detailRoutePath)
          },
          findSingleItem = { catalogItemId ->
            Timber.d("[MainScaffold.findSingleItem] catalogItemId=$catalogItemId")
            coroutineScope.launch {
              catalogDetailViewModel.find(catalogItemId)
            }
          },
          onInputSearchTextChange = { searchText ->
            coroutineScope.launch {
              catalogSearchViewModel.doSearch(searchText)
            }
          },
          onSearchCleared = {
            coroutineScope.launch {
              catalogSearchViewModel.performClearing()
            }
          },
          modifier = hostModifier.padding(paddingValues)
        )
      }
    },
    bottomBar = {
      if (AppScaffoldUtil.canShowBottomBar(windowSizeClass, isDetailDestination)) {
        MainBottomBar(
          currentRoute = currentRoute,
          navigationActions = navigationActions,
          modifier = modifier
        )
      }
    }
  )
}

private fun retrieveDefaultSelectedCatalogDetail(
  currentRoute: String,
  windowSizeClass: WindowSizeClass,
  catalogListState: CatalogListState,
  coroutineScope: CoroutineScope,
  detailViewModel: CatalogDetailViewModel
) {
  if (!AppScaffoldUtil.isTabletLandscape(windowSizeClass)) {
    return
  }

  if (currentRoute != Destination.CatalogList.route) {
    return
  }

  if (catalogListState !is CatalogListState.Listing) {
    return
  }

  coroutineScope.launch {
    detailViewModel.find(catalogListState.list.first().id)
  }
}
