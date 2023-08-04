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

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.marlonlom.demos.ajv_cappa.ui.navigation.AppNavigationActions
import dev.marlonlom.demos.ajv_cappa.ui.navigation.Destination


@Composable
fun AppNavRail(
  currentRoute: String,
  navigationActions: AppNavigationActions,
  modifier: Modifier = Modifier,
  destinations: List<Destination> = Destination.listOf()
) {
  NavigationRail(
    modifier = modifier
  ) {
    Spacer(Modifier.weight(1f))
    destinations.forEach {
      NavigationRailItem(
        selected = currentRoute == it.route,
        onClick = {
          when (it) {
            is Destination.Home -> navigationActions.navigateToHome()
            is Destination.Search -> navigationActions.navigateToSearch()
            is Destination.Settings -> navigationActions.navigateToAbout()
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
            text = stringResource(it.title),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.labelLarge
          )
        },
        alwaysShowLabel = false
      )
    }
    Spacer(Modifier.weight(1f))
  }
}
