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

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.marlonlom.demos.ajv_cappa.ui.theme.CappaTheme
import org.junit.Rule
import org.junit.Test

class TermsConditionsSettingMenuLinkUiTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun shouldClickThirdPartySoftwareSettingMenuLink() {
    composeTestRule.setContent {

      CappaTheme {
        TermsConditionsSettingMenuLink(
          CatalogSettingsRouteParams(
            uiState = CatalogSetting(
              appVersion = "1.0.0",
              isAppInDarkTheme = false,
              isUsingDynamicColors = true,
              privacyPolicyUrl = "",
              termsConditionsUrl = "https://example.org/terms-and-conditions/",
              personalDataTreatmentPolicyUrl = ""
            ),
            openLicensesSectionAction = { },
            openExternalUrlAction = {
              println("[openExternalUrlAction] url=$it")
            },
            updateBooleanSettingAction = { a, b ->
              println("key=$a; flag=$b")
            },
          )
        )
      }
    }

    composeTestRule.onNodeWithText("Terms and conditions").assertExists().performClick()
  }

}
