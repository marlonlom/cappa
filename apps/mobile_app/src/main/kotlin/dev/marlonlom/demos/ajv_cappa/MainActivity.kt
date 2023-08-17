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

package dev.marlonlom.demos.ajv_cappa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.marlonlom.demos.ajv_cappa.catalog.detail.CatalogDetailRepository
import dev.marlonlom.demos.ajv_cappa.catalog.detail.CatalogDetailViewModel
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogListRepository
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogListViewModel
import dev.marlonlom.demos.ajv_cappa.local.data.AppDatabase
import dev.marlonlom.demos.ajv_cappa.local.data.LocalDataSourceImpl
import dev.marlonlom.demos.ajv_cappa.ui.main.MainScaffold
import dev.marlonlom.demos.ajv_cappa.ui.theme.CappaTheme
import timber.log.Timber

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val windowSizeClass = calculateWindowSizeClass(this)
      Timber.d("[MainActivity] windowSizeClass=$windowSizeClass")

      val appDatabase = AppDatabase.getInstance(
        context = applicationContext
      )

      val localDataSource = LocalDataSourceImpl(
        appDatabase = appDatabase
      )

      val listRepository = CatalogListRepository(
        localDataSource = localDataSource
      )

      val catalogListViewModel: CatalogListViewModel = viewModel(
        factory = CatalogListViewModel.factory(
          repository = listRepository
        )
      )

      val catalogDetailViewModel: CatalogDetailViewModel = viewModel(
        factory = CatalogDetailViewModel.factory(
          repository = CatalogDetailRepository(
            localDataSource = localDataSource
          )
        )
      )

      CappaTheme {
        MainScaffold(
          windowSizeClass = windowSizeClass,
          catalogListViewModel = catalogListViewModel,
          catalogDetailViewModel = catalogDetailViewModel,
        )
      }
    }
  }

}
