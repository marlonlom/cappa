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

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.marlonlom.demos.ajv_cappa.local.data.AppSetting
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Catalog settings view model class.
 *
 * @author marlonlom
 *
 * @property repository catalog settings repository dependency.
 */
class CatalogSettingsViewModel(
  private val repository: CatalogSettingsRepository
) : ViewModel() {

  var uiState: MutableState<CatalogSetting?> = mutableStateOf(null)
    private set

  init {
    Timber.d("[CatalogSettingsViewModel] started fetching catalog settings")
    fetchSettings()
  }

  private fun fetchSettings() {
    viewModelScope.launch {
      val settingsList = repository.getAppSettings()
      settingsList.collect { settings ->
        val containsAllKeys = settings.all { s -> SETTING_KEYS.contains(s.key) }
        uiState.value = when {
          containsAllKeys -> generateSettingsInfoFromList(settings)
          else -> null
        }
      }
    }
  }

  private fun generateSettingsInfoFromList(
    settings: List<AppSetting>
  ) = CatalogSetting(
    appVersion = settings.find { it.key == SETTING_KEYS[0] }!!.value,
    isAppInDarkTheme = settings.find { it.key == SETTING_KEYS[1] }!!.value.toBoolean(),
    isUsingDynamicColors = settings.find { it.key == SETTING_KEYS[2] }!!.value.toBoolean(),
    privacyPolicyUrl = settings.find { it.key == SETTING_KEYS[3] }!!.value,
    termsConditionsUrl = settings.find { it.key == SETTING_KEYS[4] }!!.value,
    personalDataTreatmentPolicyUrl = settings.find { it.key == SETTING_KEYS[5] }!!.value
  )

  companion object {

    private val SETTING_KEYS = listOf(
      "app_version",
      "dark_theme",
      "dynamic_colors",
      "privacy_policy",
      "terms_conditions",
      "personal_data_treatment_policy"
    )

    /**
     * Provides a factory for creating an instance for view model.
     *
     * @param repository Catalog settings repository dependency.
     * @return
     */
    fun factory(
      repository: CatalogSettingsRepository
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatalogSettingsViewModel(repository) as T
      }
    }
  }
}

/**
 * Data class for catalog applications setting.
 *
 * @author marlonlom
 *
 * @property appVersion App version.
 * @property isAppInDarkTheme true/false if app is using dark theme.
 * @property isUsingDynamicColors true/false if app is using dark theme.
 * @property privacyPolicyUrl Privacy policy url.
 * @property termsConditionsUrl Terms plus conditions url.
 * @property personalDataTreatmentPolicyUrl Personal data treatment policy url.
 */
data class CatalogSetting(
  val appVersion: String,
  val isAppInDarkTheme: Boolean,
  val isUsingDynamicColors: Boolean,
  val privacyPolicyUrl: String,
  val termsConditionsUrl: String,
  val personalDataTreatmentPolicyUrl: String
)
