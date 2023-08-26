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

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alorma.compose.settings.ui.SettingsMenuLink
import dev.marlonlom.demos.ajv_cappa.R

@Composable
fun ThirdPartySoftwareSettingMenuLink(
  openLicensesSectionAction: () -> Unit
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_third_party_software))
    },
    onClick = {
      openLicensesSectionAction()
    }
  )
}

@Composable
fun TermsConditionsSettingMenuLink(
  settingsUiState: CatalogSetting,
  openExternalUrlAction: (String) -> Unit
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_terms_conditions))
    },
    onClick = {
      openExternalUrlAction(settingsUiState.termsConditionsUrl)
    }
  )
}

@Composable
fun PersonalDataPolicySettingMenuLink(
  settingsUiState: CatalogSetting,
  openExternalUrlAction: (String) -> Unit
) {
  SettingsMenuLink(
    title = {
      Text(
        text = stringResource(R.string.settings_label_personal_data_treatment_policy)
      )
    },
    onClick = {
      openExternalUrlAction(settingsUiState.personalDataTreatmentPolicyUrl)
    }
  )
}

@Composable
fun PrivacyPolicySettingMenuLink(
  settingsUiState: CatalogSetting,
  openExternalUrlAction: (String) -> Unit
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_privacy_policy))
    },
    onClick = {
      openExternalUrlAction(settingsUiState.privacyPolicyUrl)
    }
  )
}


@Composable
fun AppVersionSettingsMenuLink(
  settingsUiState: CatalogSetting
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_app_version))
    },
    subtitle = {
      Text(text = settingsUiState.appVersion)
    },
    onClick = {}
  )
}
