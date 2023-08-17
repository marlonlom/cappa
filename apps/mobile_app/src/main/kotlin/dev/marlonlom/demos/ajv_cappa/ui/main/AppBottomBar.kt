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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.marlonlom.demos.ajv_cappa.ui.navigation.AppNavigationActions
import dev.marlonlom.demos.ajv_cappa.ui.navigation.Destination
import timber.log.Timber

@Composable
fun MainBottomBar(
  currentRoute: String,
  navigationActions: AppNavigationActions,
  modifier: Modifier = Modifier,
  destinations: List<Destination> = Destination.listOf()
) {
  NavigationBar(
    modifier = modifier
      .fillMaxWidth(),
    containerColor = MaterialTheme.colorScheme.surface,
    contentColor = MaterialTheme.colorScheme.onSurface
  ) {
    destinations.forEach {
      NavigationBarItem(
        selected = currentRoute == it.route,
        onClick = {
          when (it) {
            is Destination.CatalogList -> navigationActions.navigateToHome()
            is Destination.CatalogSearch -> navigationActions.navigateToSearch()
            is Destination.Settings -> navigationActions.navigateToSettings()
            else -> {
              Timber.d("[MainBottomBar] Nothing happen here.")
            }
          }
        },
        icon = {
          Icon(
            it.icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer
          )
        },
        label = {
          Text(
            text = stringResource(id = it.title),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.labelLarge
          )
        }
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MainBottomBarPreview() {
  MainBottomBar(
    currentRoute = Destination.CatalogList.route,
    navigationActions = AppNavigationActions(rememberNavController())
  )
}
