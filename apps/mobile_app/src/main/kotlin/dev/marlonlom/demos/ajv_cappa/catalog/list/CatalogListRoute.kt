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

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import timber.log.Timber


@Composable
fun CatalogListRoute(
  modifier: Modifier = Modifier,
  windowSizeClass: WindowSizeClass,
  listUiState: CatalogListState,
  gotoDetailRoute: (Long) -> Unit,
) {
  if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
    CatalogListDetailScreen(
      modifier = modifier,
      uiState = listUiState,
      onSelectedItem = { productId ->
        val foundItem: ProductItem? = (listUiState as CatalogListState.Listing).list.find { it.id == productId }
        Timber.d("[CatalogListRoute.CatalogListDetailScreen] foundItem=$foundItem")
      }
    )
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
  uiState: CatalogListState,
  selectedItemId: Long? = null,
  onSelectedItem: (Long) -> Unit
) {

}
