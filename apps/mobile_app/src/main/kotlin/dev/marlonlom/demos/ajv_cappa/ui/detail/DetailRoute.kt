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

package dev.marlonlom.demos.ajv_cappa.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import dev.marlonlom.demos.ajv_cappa.R
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogItem
import dev.marlonlom.demos.ajv_cappa.remote.data.Punctuation
import dev.marlonlom.demos.ajv_cappa.ui.home.toSentenceCase
import dev.marlonlom.demos.ajv_cappa.ui.main.MainScaffoldUtil
import dev.marlonlom.demos.ajv_cappa.ui.main.isCompactWidth

@Composable
fun DetailRoute(
  windowSizeClass: WindowSizeClass, catalogItem: CatalogItem
) {
  DetailScreen(windowSizeClass = windowSizeClass, catalogItem = catalogItem)
}


@Composable
fun DetailScreen(windowSizeClass: WindowSizeClass, catalogItem: CatalogItem) {

  val paddingValues = when {
    windowSizeClass.isCompactWidth -> PaddingValues(horizontal = 20.dp, vertical = 0.dp)
    MainScaffoldUtil.isMobileLandscape(windowSizeClass) -> PaddingValues(horizontal = 60.dp, vertical = 20.dp)
    MainScaffoldUtil.isTabletLandscape(windowSizeClass) -> PaddingValues(all = 60.dp)
    else -> PaddingValues(horizontal = 60.dp)
  }

  when {
    windowSizeClass.isCompactWidth -> {
      DetailContentPortrait(catalogItem, paddingValues)
    }
  }
}

@Composable
private fun ProductTitleText(catalogItem: CatalogItem) {
  Text(
    text = catalogItem.title.toSentenceCase,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 10.dp),
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.headlineMedium,
    maxLines = 2,
    minLines = 1
  )
}

@Composable
private fun ProductAsyncImage(catalogItem: CatalogItem) {
  SubcomposeAsyncImage(
    model = catalogItem.picture,
    contentDescription = null,
    loading = {
      CircularProgressIndicator()
    },
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .padding(horizontal = 60.dp)
      .padding(top = 80.dp)
      .clip(CircleShape)
      .size(200.dp)
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
      .paddingFromBaseline(top = 40.dp, bottom = 8.dp),
    text = stringResource(id = R.string.detail_text_points),
    maxLines = 1,
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.bodyLarge,
    fontWeight = FontWeight.Bold
  )
}

@Composable
fun ProductPointsGridItem(punctuation: Punctuation) {
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
        text = punctuation.pointsQty.toString(),
        style = MaterialTheme.typography.titleLarge
      )
    }

  }
}

@Composable
fun DetailContentPortrait(catalogItem: CatalogItem, paddingValues: PaddingValues) {
  LazyVerticalGrid(
    modifier = Modifier
      .fillMaxWidth()
      .padding(paddingValues),
    columns = GridCells.Fixed(2),
    contentPadding = PaddingValues(16.dp),
    horizontalArrangement = Arrangement.spacedBy(20.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {

    item(span = { GridItemSpan(maxLineSpan) }) {
      ProductAsyncImage(catalogItem)
    }

    item(span = { GridItemSpan(maxLineSpan) }) {
      ProductTitleText(catalogItem)
    }

    item(span = { GridItemSpan(maxLineSpan) }) {
      PointsTitleText()
    }

    items(catalogItem.punctuations) { punctuation ->
      ProductPointsGridItem(punctuation = punctuation)
    }
  }
}
