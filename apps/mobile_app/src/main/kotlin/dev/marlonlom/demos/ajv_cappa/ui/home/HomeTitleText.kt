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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.marlonlom.demos.ajv_cappa.R
import dev.marlonlom.demos.ajv_cappa.ui.main.MainScaffoldUtil


@Composable
fun HomeTitleText(windowSizeClass: WindowSizeClass) {
  Text(
    text = buildAnnotatedString {
      withStyle(
        style = SpanStyle(
          fontWeight = FontWeight.Light
        )
      ) {
        val textTitleEnjoy = stringResource(R.string.home_title_enjoy)
        append(
          when {
            MainScaffoldUtil.isMobileLandscape(windowSizeClass) -> "$textTitleEnjoy "
            else -> "$textTitleEnjoy \n"
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
          stringResource(R.string.home_title_exclusive_items)
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
