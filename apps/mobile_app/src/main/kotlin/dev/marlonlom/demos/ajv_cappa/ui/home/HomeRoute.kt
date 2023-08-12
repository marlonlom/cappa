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

package dev.marlonlom.demos.ajv_cappa.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogItem
import dev.marlonlom.demos.ajv_cappa.ui.common.CatalogUiState
import dev.marlonlom.demos.ajv_cappa.ui.detail.DetailRoute
import dev.marlonlom.demos.ajv_cappa.ui.main.MainScaffoldUtil

@Composable
fun HomeRoute(
  windowSizeClass: WindowSizeClass,
  uiState: CatalogUiState,
  modifier: Modifier = Modifier,
  navigateToProductDetailRoute: (Long) -> Unit,
  selectedItem: CatalogItem?
) {

  when (uiState) {
    is CatalogUiState.Home -> {
      val catalogItems = uiState.list
      when {
        catalogItems.isEmpty() -> {
          TODO()
        }

        (selectedItem != null) and MainScaffoldUtil.isTabletLandscape(windowSizeClass) -> {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            LazyCatalogGrid(
              modifier = modifier,
              windowSizeClass = windowSizeClass,
              catalogItems = catalogItems,
              navigateToProductDetailRoute = navigateToProductDetailRoute
            )
            Column(modifier = Modifier.fillMaxWidth()) {
              DetailRoute(
                windowSizeClass = windowSizeClass,
                catalogItem = selectedItem!!
              )
            }
          }
        }

        (selectedItem != null) and !MainScaffoldUtil.isTabletLandscape(windowSizeClass) -> {
          DetailRoute(
            windowSizeClass = windowSizeClass,
            catalogItem = selectedItem!!
          )
        }

        else -> {
          LazyCatalogGrid(
            windowSizeClass = windowSizeClass,
            catalogItems = catalogItems,
            navigateToProductDetailRoute = navigateToProductDetailRoute
          )
        }
      }
    }

    is CatalogUiState.Error -> TODO()

    CatalogUiState.Loading -> TODO()
  }
}
