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

import android.os.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import dev.marlonlom.demos.ajv_cappa.R

@Composable
fun DarkThemeSettingSwitch(
  routeParams: CatalogSettingsRouteParams
) {
  SettingsSwitch(
    title = {
      Text(text = stringResource(R.string.settings_label_dark_theme))
    },
    state = rememberBooleanSettingState(
      routeParams.settingsUiState!!.isAppInDarkTheme
    ),
    onCheckedChange = { toggled ->
      routeParams.updateBooleanSettingAction("dark_theme", toggled)
    }
  )
}

@Composable
fun DynamicColorsSettingSwitch(
  routeParams: CatalogSettingsRouteParams
) {
  SettingsSwitch(
    enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    title = {
      Text(text = stringResource(R.string.settings_label_dynamic_colors))
    },
    state = rememberBooleanSettingState(routeParams.settingsUiState!!.isUsingDynamicColors),
    onCheckedChange = { toggled ->
      routeParams.updateBooleanSettingAction("dynamic_colors", toggled)
    }
  )
}

@Composable
fun ThirdPartySoftwareSettingMenuLink(
  routeParams: CatalogSettingsRouteParams
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_third_party_software))
    },
    onClick = {
      routeParams.openLicensesSectionAction()
    }
  )
}

@Composable
fun TermsConditionsSettingMenuLink(
  routeParams: CatalogSettingsRouteParams
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_terms_conditions))
    },
    onClick = {
      routeParams.openExternalUrlAction(
        routeParams.settingsUiState!!.termsConditionsUrl
      )
    }
  )
}

@Composable
fun PersonalDataPolicySettingMenuLink(
  routeParams: CatalogSettingsRouteParams
) {
  SettingsMenuLink(
    title = {
      Text(
        text = stringResource(R.string.settings_label_personal_data_treatment_policy)
      )
    },
    onClick = {
      routeParams.openExternalUrlAction(
        routeParams.settingsUiState!!.personalDataTreatmentPolicyUrl
      )
    }
  )
}

@Composable
fun PrivacyPolicySettingMenuLink(
  routeParams: CatalogSettingsRouteParams
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_privacy_policy))
    },
    onClick = {
      routeParams.openExternalUrlAction(
        routeParams.settingsUiState!!.privacyPolicyUrl
      )
    }
  )
}


@Composable
fun AppVersionSettingsMenuLink(
  routeParams: CatalogSettingsRouteParams
) {
  SettingsMenuLink(
    title = {
      Text(text = stringResource(R.string.settings_label_app_version))
    },
    subtitle = {
      Text(text = routeParams.settingsUiState!!.appVersion)
    },
    onClick = {}
  )
}
