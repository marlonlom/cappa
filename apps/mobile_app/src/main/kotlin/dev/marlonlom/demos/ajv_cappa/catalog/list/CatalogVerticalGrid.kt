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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.marlonlom.demos.ajv_cappa.R
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.ui.main.AppScaffoldUtil

@Composable
fun CatalogVerticalGrid(
  windowSizeClass: WindowSizeClass,
  onItemSelected: (Long) -> Unit,
  modifier: Modifier = Modifier,
  listUiState: CatalogListState
) {
  val gridColumnsCount = when {
    AppScaffoldUtil.isMobileLandscape(windowSizeClass) -> 4
    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> 3
    else -> 2
  }

  val columnBottomPaddingHorizontal = when (windowSizeClass.widthSizeClass) {
    WindowWidthSizeClass.Compact -> 20.dp
    else -> 60.dp
  }

  val gridItemHeight = when (windowSizeClass.widthSizeClass) {
    WindowWidthSizeClass.Expanded -> 60.dp
    else -> 120.dp
  }

  Column(
    modifier = modifier
      .padding(horizontal = columnBottomPaddingHorizontal),
  ) {
    CatalogTitleText(windowSizeClass = windowSizeClass)

    Divider(modifier = Modifier.padding(bottom = 10.dp))

    when (listUiState) {
      is CatalogListState.Listing -> {
        LazyVerticalGrid(
          columns = GridCells.Fixed(gridColumnsCount),
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

          items(items = listUiState.list) { item: ProductItem ->
            CatalogVerticalGridCard(
              catalogItem = item,
              gridItemHeight = gridItemHeight
            ) { catalogItem: ProductItem ->
              onItemSelected(catalogItem.id)
            }
          }

          item(
            span = { GridItemSpan(gridColumnsCount) }
          ) {
            Spacer(modifier = Modifier.height(4.dp))
          }

        }
      }

      CatalogListState.Empty -> {
        Text(
          text = "Catalog is empty :( ",
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center
        )
      }

      CatalogListState.Loading -> {
        CatalogListLoadingText()
      }
    }

  }
}

@Composable
private fun CatalogListLoadingText() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp),
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CircularProgressIndicator(modifier = Modifier.size(48.dp))

    Text(
      text = stringResource(R.string.home_text_list_loading),
      modifier = Modifier
        .fillMaxWidth()
        .paddingFromBaseline(top = 40.dp, bottom = 20.dp),
      textAlign = TextAlign.Center
    )
  }
}
