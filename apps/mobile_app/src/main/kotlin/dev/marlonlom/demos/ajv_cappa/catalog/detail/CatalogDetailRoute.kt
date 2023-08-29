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

package dev.marlonlom.demos.ajv_cappa.catalog.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import dev.marlonlom.demos.ajv_cappa.R
import dev.marlonlom.demos.ajv_cappa.catalog.list.toSentenceCase
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItemPoint
import dev.marlonlom.demos.ajv_cappa.ui.main.AppScaffoldUtil
import dev.marlonlom.demos.ajv_cappa.ui.main.isCompactWidth

@Composable
fun CatalogDetailRoute(
  windowSizeClass: WindowSizeClass,
  catalogItem: CatalogDetail?,
  onBackPressed: () -> Unit
) {
  if (catalogItem != null) {
    DetailScreen(
      windowSizeClass = windowSizeClass,
      catalogDetail = catalogItem
    )
  } else {
    Text(
      text = "Catalog item not found :( ",
      modifier = Modifier.fillMaxSize(),
      textAlign = TextAlign.Center
    )
  }
  BackHandler {
    onBackPressed()
  }
}


@Composable
fun DetailScreen(
  windowSizeClass: WindowSizeClass,
  catalogDetail: CatalogDetail?
) {

  val paddingValues = when {
    windowSizeClass.isCompactWidth -> PaddingValues(horizontal = 20.dp, vertical = 0.dp)
    AppScaffoldUtil.isMobileLandscape(windowSizeClass) -> PaddingValues(horizontal = 60.dp, vertical = 20.dp)
    AppScaffoldUtil.isTabletLandscape(windowSizeClass) -> PaddingValues(all = 20.dp)
    else -> PaddingValues(horizontal = 60.dp)
  }

  val productImageSize = when {
    AppScaffoldUtil.isMobileLandscape(windowSizeClass) -> 120.dp
    AppScaffoldUtil.isTabletLandscape(windowSizeClass) -> 100.dp
    else -> 200.dp
  }

  val isHeadingLandscape = when {
    AppScaffoldUtil.isMobileLandscape(windowSizeClass) -> true
    AppScaffoldUtil.isTabletLandscape(windowSizeClass) -> true
    else -> false
  }

  DetailContent(
    catalogItem = catalogDetail!!.product,
    punctuations = catalogDetail.points,
    paddingValues = paddingValues,
    imageSize = productImageSize,
    isHeadingLandscape = isHeadingLandscape
  )

}

@Composable
private fun ProductTitleText(
  catalogItem: ProductItem,
  titleTextAlign: TextAlign = TextAlign.Center
) {
  Text(
    text = catalogItem.title.toSentenceCase,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 5.dp),
    textAlign = titleTextAlign,
    style = MaterialTheme.typography.headlineMedium,
    maxLines = 2,
    minLines = 1
  )
}

@Composable
private fun ProductAsyncImage(catalogItem: ProductItem, imageSize: Dp) {
  SubcomposeAsyncImage(
    model = catalogItem.picture,
    contentDescription = null,
    loading = {
      CircularProgressIndicator(Modifier.size(48.dp))
    },
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .width(imageSize)
      .height(imageSize)
      .padding(all = 10.dp)
      .clip(CircleShape)
      .border(
        width = 2.dp,
        color = MaterialTheme.colorScheme.secondary,
        shape = CircleShape
      )
  )
}

@Composable
private fun PointsTitleText() {
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .paddingFromBaseline(top = 20.dp, bottom = 8.dp),
    text = stringResource(id = R.string.detail_text_points),
    maxLines = 1,
    textAlign = TextAlign.Start,
    style = MaterialTheme.typography.bodyLarge,
    fontWeight = FontWeight.Bold
  )
}

@Composable
fun ProductPointsGridItem(punctuation: ProductItemPoint) {
  Surface(
    modifier = Modifier
      .fillMaxWidth(),
    shape = MaterialTheme.shapes.medium,
    color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onClick = { }) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(
        text = punctuation.label.toSentenceCase,
        style = MaterialTheme.typography.titleSmall
      )
      Text(
        text = punctuation.points.toString(),
        style = MaterialTheme.typography.titleLarge
      )
    }

  }
}

@Composable
private fun CatalogDetailHeading(
  catalogItem: ProductItem,
  imageSize: Dp,
  isHeadingLandscape: Boolean
) {
  if (isHeadingLandscape) {
    Row(
      modifier = Modifier
        .fillMaxWidth(0.5f)
        .padding(20.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      ProductAsyncImage(catalogItem, imageSize)
      ProductTitleText(catalogItem, TextAlign.Start)
    }
  } else {
    Column(
      modifier = Modifier.padding(top = 40.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      ProductAsyncImage(catalogItem, imageSize)
      ProductTitleText(catalogItem)
    }
  }
}

@Composable
fun DetailContent(
  catalogItem: ProductItem,
  punctuations: List<ProductItemPoint>,
  paddingValues: PaddingValues,
  imageSize: Dp,
  isHeadingLandscape: Boolean = false
) {
  LazyVerticalGrid(
    modifier = Modifier
      .fillMaxWidth()
      .padding(paddingValues),
    columns = GridCells.Adaptive(120.dp),
    contentPadding = PaddingValues(16.dp),
    horizontalArrangement = Arrangement.spacedBy(20.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {

    item(span = { GridItemSpan(maxLineSpan) }) {
      CatalogDetailHeading(
        catalogItem = catalogItem,
        imageSize = imageSize,
        isHeadingLandscape = isHeadingLandscape
      )
    }

    item(span = { GridItemSpan(maxLineSpan) }) {
      PointsTitleText()
    }

    items(punctuations) { punctuation ->
      ProductPointsGridItem(punctuation = punctuation)
    }
  }
}
