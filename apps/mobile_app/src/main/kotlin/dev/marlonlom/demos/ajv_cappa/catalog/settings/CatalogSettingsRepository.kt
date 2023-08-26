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

import dev.marlonlom.demos.ajv_cappa.local.data.AppSetting
import dev.marlonlom.demos.ajv_cappa.local.data.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Catalog list repository class.
 *
 * @author marlonlom
 *
 * @property localDataSource local data source dependency
 */
class CatalogSettingsRepository(
  private val localDataSource: LocalDataSource,
  private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

  /**
   * Returns catalog settings list as flow.
   *
   * @return Catalog settings list as flow.
   *
   */
  fun getAppSettings() = localDataSource.getAppSettings()

  /**
   * Updates boolean setting.
   *
   * @param key Boolean setting key name.
   * @param flag true/false for boolean setting value.
   */
  suspend fun updateBooleanSetting(
    key: String, flag: String
  ) = withContext(coroutineDispatcher) {
    localDataSource.updateBooleanSetting(AppSetting(key, flag))
  }
}
