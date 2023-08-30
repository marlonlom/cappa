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

package dev.marlonlom.demos.ajv_cappa.catalog.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.marlonlom.demos.ajv_cappa.ui.theme.CappaTheme
import org.junit.Rule
import org.junit.Test

internal class DynamicColorSettingSwitchUiTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun shouldToggleDynamicColorSwitch() {
    composeTestRule.setContent {
      var clicked by remember { mutableStateOf(false) }

      CappaTheme {
        DynamicColorsSettingSwitch(
          CatalogSettingsRouteParams(
            uiState = CatalogSetting(
              appVersion = "1.0.0",
              isAppInDarkTheme = clicked,
              isUsingDynamicColors = true,
              privacyPolicyUrl = "",
              termsConditionsUrl = "",
              personalDataTreatmentPolicyUrl = ""
            ),
            openLicensesSectionAction = {},
            openExternalUrlAction = {},
            updateBooleanSettingAction = { a, b ->
              clicked = b
              println("key=$a; flag=$b")
            },
          )
        )
      }
    }

    val onNodeWithText = composeTestRule.onNodeWithText("Dynamic colors")
    onNodeWithText.assertExists().performClick()
  }

}
