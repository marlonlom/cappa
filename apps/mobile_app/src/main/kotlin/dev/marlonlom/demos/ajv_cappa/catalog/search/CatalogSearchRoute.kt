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

package dev.marlonlom.demos.ajv_cappa.catalog.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.marlonlom.demos.ajv_cappa.catalog.detail.CatalogDetail
import dev.marlonlom.demos.ajv_cappa.catalog.detail.DetailScreen
import dev.marlonlom.demos.ajv_cappa.ui.main.AppScaffoldUtil
import timber.log.Timber

@Composable
fun CatalogSearchRoute(
  modifier: Modifier = Modifier,
  windowSizeClass: WindowSizeClass,
  searchUiState: CatalogSearchState,
  detailUiState: CatalogDetail?,
  gotoDetailRoute: (Long) -> Unit,
  findSingleItem: (Long) -> Unit,
  onInputSearchTextChange: (String) -> Unit,
  onSearchCleared: () -> Unit
) {
  Timber.d("[CatalogSearchRoute] searchUiState=$searchUiState")
  if (AppScaffoldUtil.isTabletLandscape(windowSizeClass)) {
    CatalogSearchDetailScreen(
      modifier = modifier,
      windowSizeClass = windowSizeClass,
      searchUiState = searchUiState,
      detailUiState = detailUiState,
      onSelectedItem = { productId ->
        findSingleItem(productId)
      },
      onInputSearchTextChange = {
        onInputSearchTextChange(it)
      }, onSearchCleared = onSearchCleared
    )
  } else {
    CatalogSearchScreen(
      modifier = modifier,
      searchUiState = searchUiState,
      onInputSearchTextChange = { searchInput ->
        Timber.d("[CatalogSearchRoute.CatalogSearchScreen.onInputSearchTextChange] searchInput=$searchInput")
        onInputSearchTextChange(searchInput)
      },
      onSelectedItem = { productId ->
        Timber.d("[CatalogSearchRoute.CatalogSearchScreen.onSelectedItem] productId=$productId")
        gotoDetailRoute(productId)
      }, onSearchCleared = onSearchCleared,
      windowSizeClass = windowSizeClass
    )
  }
}


@Composable
fun CatalogSearchDetailScreen(
  modifier: Modifier,
  windowSizeClass: WindowSizeClass,
  searchUiState: CatalogSearchState,
  detailUiState: CatalogDetail?,
  onSelectedItem: (Long) -> Unit,
  onInputSearchTextChange: (String) -> Unit,
  onSearchCleared: () -> Unit
) {
  Row {
    CatalogSearchScreen(
      modifier = modifier,
      searchUiState = searchUiState,
      onInputSearchTextChange = { searchInput ->
        onInputSearchTextChange(searchInput)
        Timber.d("[CatalogSearchRoute.CatalogSearchDetailScreen.onInputSearchTextChange] searchInput=$searchInput")
      },
      onSelectedItem = onSelectedItem,
      onSearchCleared = onSearchCleared,
      windowSizeClass = windowSizeClass
    )
    if (detailUiState != null) {
      DetailScreen(
        windowSizeClass = windowSizeClass,
        catalogDetail = detailUiState
      )
    } else {
      Column(
        modifier = Modifier.fillMaxSize()
      ) {}
    }
  }
}
