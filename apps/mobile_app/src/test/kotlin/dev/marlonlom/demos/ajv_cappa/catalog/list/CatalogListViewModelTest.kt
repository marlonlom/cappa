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

import dev.marlonlom.demos.ajv_cappa.local.data.FakeLocalDataSource
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogDataService
import dev.marlonlom.demos.ajv_cappa.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class CatalogListViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewModel: CatalogListViewModel

  @Test
  fun shouldCheckUiStateValueIsNotEmptyList() = runTest {
    viewModel = CatalogListViewModel(
      CatalogListRepository(
        FakeLocalDataSource(
          CatalogDataService()
        )
      )
    )
    val uiState = viewModel.uiState
    Assert.assertNotNull(uiState)
    when (uiState.value) {
      is CatalogListState.Listing -> {
        val home = uiState.value as CatalogListState.Listing
        Assert.assertTrue(home.list.isNotEmpty())
      }

      else -> Assert.fail()
    }
  }
}
