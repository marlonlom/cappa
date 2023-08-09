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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.marlonlom.demos.ajv_cappa.main.data.CatalogItem
import timber.log.Timber
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CatalogItemCard(
  catalogItem: CatalogItem,
  gridItemHeight: Dp,
  onCardClicked: (CatalogItem) -> Unit
) {
  Card(
    shape = MaterialTheme.shapes.medium,
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.tertiaryContainer,
      contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ),
    onClick = {
      Timber.d("[LazyCatalogGrid] clicked card item: ${catalogItem.title}")
      onCardClicked(catalogItem)
    }
  ) {
    AsyncImage(
      model = catalogItem.picture,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .height(gridItemHeight)
    )
    Text(
      text = catalogItem.title.toSentenceCase,
      modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .padding(bottom = 10.dp),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.labelLarge,
      maxLines = 2,
      minLines = 2
    )
  }
}

inline val String.toSentenceCase: String
  get() = this.lowercase(Locale.getDefault()).replaceFirstChar { it.uppercaseChar() }
