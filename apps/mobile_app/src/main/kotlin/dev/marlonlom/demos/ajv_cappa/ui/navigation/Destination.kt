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

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.marlonlom.demos.ajv_cappa.R

/**
 * Application navigation destination sealed class.
 *
 * @property route
 * @property title
 * @property icon
 */
sealed class Destination(
  val route: String,
  @StringRes val title: Int,
  val icon: ImageVector
) {

  object Home : Destination(
    route = "home",
    title = R.string.destination_home,
    icon = Icons.Rounded.Home
  )

  object Search : Destination(
    route = "search",
    title = R.string.destination_search,
    icon = Icons.Rounded.Search
  )

  object Settings : Destination(
    route = "settings",
    title = R.string.destination_settings,
    icon = Icons.Rounded.Settings
  )

  object Detail : Destination(
    route = "detail/{$itemIdArg}",
    title = R.string.destination_detail,
    icon = Icons.Rounded.Info
  ) {
    fun createRoute(itemId: Long) = "detail/$itemId"

    val arguments = listOf(navArgument(itemIdArg) {
      type = NavType.LongType
      defaultValue = defaultId
    })

  }


  companion object {

    private const val defaultId = -1L

    const val itemIdArg = "itemId"

    fun listOf() = listOf(Home, Search, Settings)

  }
}

/**
 * Application navigation actions class
 *
 * @property navController navigation controller
 */
class AppNavigationActions(private val navController: NavHostController) {

  /**
   * Action for navigation to Home destination
   *
   */
  fun navigateToHome() {
    clearNavigationArguments()
    navController.navigate(route = Destination.Home.route) {
      useDefaultNavOptions(navController)
    }
  }

  /**
   * Action for navigation to Search destination
   *
   */
  fun navigateToSearch() {
    clearNavigationArguments()
    navController.navigate(route = Destination.Search.route) {
      useDefaultNavOptions(navController)
    }
  }

  /**
   * Action for navigation to Search destination
   *
   */
  fun navigateToSettings() {
    clearNavigationArguments()
    navController.navigate(route = Destination.Settings.route) {
      useDefaultNavOptions(navController)
    }
  }

  private fun clearNavigationArguments() {
    navController.currentBackStackEntry?.arguments?.clear()
  }

  private fun NavOptionsBuilder.useDefaultNavOptions(navController: NavHostController) {
    popUpTo(navController.graph.findStartDestination().id) {
      saveState = true
    }
    launchSingleTop = true
    restoreState = true
  }

}
