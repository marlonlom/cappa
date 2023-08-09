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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.marlonlom.demos.ajv_cappa.ui.navigation.AppNavigationActions
import dev.marlonlom.demos.ajv_cappa.ui.navigation.Destination
import dev.marlonlom.demos.ajv_cappa.ui.navigation.NavigationHost
import timber.log.Timber

@Composable
fun MainScaffold(
  windowSizeClass: WindowSizeClass,
  modifier: Modifier = Modifier
) {
  val navController: NavHostController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute =
    navBackStackEntry?.destination?.route ?: Destination.Home.route

  val navigationActions = AppNavigationActions(
    navController = navController
  )

  val isExpandedWidth = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

  val isDetailDestination = !Destination.listOf().map { it.route }.contains(currentRoute)

  Scaffold(
    modifier = modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.surface),
    topBar = {
      if (isDetailDestination) {
        AppTopBar(
          onNavigationIconClicked = {
            navController.popBackStack()
          }
        )
      }
    },
    content = {
      Row {
        if (isExpandedWidth && !isDetailDestination) {
          AppNavRail(
            currentRoute = currentRoute,
            navigationActions = navigationActions
          )
        }

        val modifier = if (isExpandedWidth) Modifier.width(334.dp) else Modifier.fillMaxWidth()

        NavigationHost(
          navController = navController,
          windowSizeClass = windowSizeClass,
          paddingValues = it,
          modifier = modifier,
          navigateToProductDetailRoute = { catalogItemId ->
            when {
              !isExpandedWidth -> {
                val itemRouteWithId = Destination.Detail.getItemRouteWithId(catalogItemId)
                Timber.d("[MainScaffold] navigateToDetail(itemRouteWithId=$itemRouteWithId)")
                navigationActions.navigateToDetail(itemRouteWithId = itemRouteWithId)
              }

              else -> {
                Timber.d("[MainScaffold] navigateToProductDetailRoute(catalogItemId=$catalogItemId)")
              }
            }
          }
        )

        if (isExpandedWidth) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(60.dp)
              .background(color = MaterialTheme.colorScheme.tertiaryContainer)
              .clip(RoundedCornerShape(20.dp))
          ) {
            Text(text = "Detail")
          }
        }
      }
    },
    bottomBar = {
      if (!isExpandedWidth && !isDetailDestination) {
        MainBottomBar(
          currentRoute = currentRoute,
          navigationActions = navigationActions,
          modifier = modifier
        )
      }
    }
  )
}
