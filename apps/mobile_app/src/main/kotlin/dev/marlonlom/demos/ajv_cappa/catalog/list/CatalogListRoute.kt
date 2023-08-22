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

package dev.marlonlom.demos.ajv_cappa.catalog.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.marlonlom.demos.ajv_cappa.catalog.detail.CatalogDetail
import dev.marlonlom.demos.ajv_cappa.catalog.detail.DetailScreen
import dev.marlonlom.demos.ajv_cappa.ui.main.MainScaffoldUtil
import timber.log.Timber

@Composable
fun CatalogListRoute(
  modifier: Modifier = Modifier,
  windowSizeClass: WindowSizeClass,
  listUiState: CatalogListState,
  gotoDetailRoute: (Long) -> Unit,
  findSingleItem: (Long) -> Unit,
  detailUiState: CatalogDetail?,
) {
  if (MainScaffoldUtil.isTabletLandscape(windowSizeClass)) {
    CatalogListDetailScreen(
      modifier = modifier,
      windowSizeClass = windowSizeClass,
      listUiState = listUiState,
      detailUiState = detailUiState
    ) { productId ->
      findSingleItem(productId)
    }
  } else {
    CatalogListScreen(
      modifier = modifier,
      windowSizeClass = windowSizeClass,
      listUiState = listUiState,
      onSelectedItem = { productId ->
        Timber.d("[CatalogListRoute.CatalogListScreen.onSelectedItem] productId=$productId")
        gotoDetailRoute(productId)
      }
    )
  }
}

@Composable
fun CatalogListScreen(
  modifier: Modifier,
  listUiState: CatalogListState,
  onSelectedItem: (Long) -> Unit,
  windowSizeClass: WindowSizeClass
) {
  CatalogVerticalGrid(
    modifier = modifier,
    windowSizeClass = windowSizeClass,
    listUiState = listUiState,
    onItemSelected = onSelectedItem
  )
}

@Composable
fun CatalogListDetailScreen(
  modifier: Modifier,
  windowSizeClass: WindowSizeClass,
  listUiState: CatalogListState,
  detailUiState: CatalogDetail?,
  onSelectedItem: (Long) -> Unit
) {
  Row {
    CatalogVerticalGrid(
      modifier = modifier,
      windowSizeClass = windowSizeClass,
      listUiState = listUiState,
      onItemSelected = onSelectedItem
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
