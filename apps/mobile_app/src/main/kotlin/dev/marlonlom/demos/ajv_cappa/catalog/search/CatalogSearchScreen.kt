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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.marlonlom.demos.ajv_cappa.R
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogVerticalGridCard
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.ui.main.AppScaffoldUtil
import timber.log.Timber

@Composable
fun CatalogSearchScreen(
  modifier: Modifier,
  searchUiState: CatalogSearchState,
  onInputSearchTextChange: (String) -> Unit,
  onSelectedItem: (Long) -> Unit,
  onSearchCleared: () -> Unit,
  windowSizeClass: WindowSizeClass
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
    CatalogSearchTitleText(windowSizeClass = windowSizeClass)
    CatalogSearchInput(
      searchUiState = searchUiState,
      onInputSearchTextChange = onInputSearchTextChange,
      onSearchCleared = onSearchCleared
    )

    when (searchUiState) {
      is CatalogSearchState.EmptyResults -> TODO()
      is CatalogSearchState.Ready -> {
      }

      is CatalogSearchState.Searching -> {
        CatalogSearchLoadingText()
      }

      is CatalogSearchState.WithResults -> {
        LazyVerticalGrid(
          columns = GridCells.Fixed(gridColumnsCount),
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

          items(items = searchUiState.list) { item: ProductItem ->
            CatalogVerticalGridCard(
              catalogItem = item,
              gridItemHeight = gridItemHeight
            ) { catalogItem: ProductItem ->
              onSelectedItem(catalogItem.id)
            }
          }

          item(
            span = { GridItemSpan(gridColumnsCount) }
          ) {
            Spacer(modifier = Modifier.height(4.dp))
          }

        }
      }
    }
  }

}

@Composable
fun CatalogSearchInput(
  searchUiState: CatalogSearchState,
  onInputSearchTextChange: (String) -> Unit,
  onSearchCleared: () -> Unit
) {
  val focusManager = LocalFocusManager.current

  val textState = remember {
    mutableStateOf(
      TextFieldValue(
        searchUiState.searchInput,
        TextRange(0, searchUiState.searchInput.length)
      )
    )
  }

  var showClearIcon by rememberSaveable { mutableStateOf(false) }
  showClearIcon = searchUiState.searchInput.isNotEmpty()

  TextField(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 20.dp),
    value = textState.value,
    onValueChange = {
      textState.value = it
    },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Done
    ),
    keyboardActions = KeyboardActions(
      onDone = {
        focusManager.clearFocus()
        Timber.d("[CatalogSearchInput.TextField.keyboardActions] onInputSearchTextChange(${textState.value.text})")
        onInputSearchTextChange(textState.value.text)
      }
    ),
    shape = RectangleShape,
    leadingIcon = {
      Icon(Icons.Rounded.Search, contentDescription = null)
    },
    trailingIcon = {
      if (showClearIcon) {
        IconButton(onClick = {
          textState.value = TextFieldValue("")
          focusManager.clearFocus()
          onSearchCleared()
        }) {
          Icon(
            imageVector = Icons.Rounded.Clear,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = "Clear icon"
          )
        }
      }
    },
    singleLine = true
  )
}

@Composable
fun CatalogSearchTitleText(windowSizeClass: WindowSizeClass) {
  Text(
    text = buildAnnotatedString {
      withStyle(
        style = SpanStyle(
          fontWeight = FontWeight.Light
        )
      ) {
        val textTitleSearch = stringResource(R.string.search_title_idem)
        append(
          when {
            AppScaffoldUtil.isMobileLandscape(windowSizeClass) -> "$textTitleSearch "
            else -> "$textTitleSearch \n"
          }
        )
      }
      withStyle(
        style = SpanStyle(
          fontWeight = FontWeight.Bold,
          fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
      ) {
        append(
          stringResource(R.string.search_title_catalog_products)
        )
      }
    },
    modifier = Modifier
      .fillMaxWidth()
      .padding(
        top = 40.dp,
        bottom = 20.dp
      ),
    color = MaterialTheme.colorScheme.onSurface,
    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
  )
}


@Composable
private fun CatalogSearchLoadingText() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp),
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CircularProgressIndicator(modifier = Modifier.size(48.dp))

    Text(
      text = stringResource(R.string.search_text_list_loading),
      modifier = Modifier
        .fillMaxWidth()
        .paddingFromBaseline(top = 40.dp, bottom = 20.dp),
      textAlign = TextAlign.Center
    )
  }
}
