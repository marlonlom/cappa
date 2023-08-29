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

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import dev.marlonlom.demos.ajv_cappa.ui.theme.CappaTheme
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterial3WindowSizeClassApi
internal class CatalogTitleTextUiTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun shouldDisplayTitleText() {
    composeTestRule.setContent {
      CappaTheme {
        CatalogTitleText(
          windowSizeClass = WindowSizeClass.calculateFromSize(
            DpSize(360.dp, 480.dp)
          )
        )
      }
    }

    composeTestRule
      .onNodeWithText("enjoy \nExclusive products")
      .assertExists()
  }

  @Test
  fun shouldDisplayTitleTextForLandscape() {
    composeTestRule.setContent {
      CappaTheme {
        CatalogTitleText(
          windowSizeClass = WindowSizeClass.calculateFromSize(
            DpSize(840.dp, 360.dp)
          )
        )
      }
    }

    composeTestRule
      .onNodeWithText("enjoy Exclusive products")
      .assertExists()
  }
}
