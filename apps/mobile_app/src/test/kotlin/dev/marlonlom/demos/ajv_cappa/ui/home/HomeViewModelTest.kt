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

import dev.marlonlom.demos.ajv_cappa.main.data.CatalogDataService
import dev.marlonlom.demos.ajv_cappa.main.data.CatalogItem
import dev.marlonlom.demos.ajv_cappa.main.data.Punctuations
import dev.marlonlom.demos.ajv_cappa.main.data.Response
import dev.marlonlom.demos.ajv_cappa.main.data.successOr
import dev.marlonlom.demos.ajv_cappa.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewModel: HomeViewModel

  @Before
  fun setUp() {
    viewModel = HomeViewModel(CatalogDataService())
  }

  @Test
  fun shouldCheckUiStateValueIsNotEmptyList() = runTest {
    val uiState = viewModel.uiState
    assertNotNull(uiState)
    assertTrue(uiState.value is Response.Success)
    assertTrue(uiState.value.successOr(emptyList()).isNotEmpty())
  }

  @Test
  fun shouldCheckUiStateSingleValueExists() = runTest {
    val expectedItem = CatalogItem(
      id = 15396L,
      title = "Granizado",
      picture = "https://juanvaldez.com/wp-content/uploads/2022/10/Granizado-juan-Valdez.jpg",
      punctuations = listOf(
        Punctuations(
          label = "Mediano",
          pointsQty = 2225
        )
      )
    )

    val uiState = viewModel.uiState
    val catalogItem = uiState.value.successOr(emptyList()).find { it.id == 15396L }
    assertNotNull(uiState)
    assertNotNull(catalogItem)
  }

  @Test
  fun shouldValidateExpectedCatalogItemNotExist() {
    val expectedItem = CatalogItem(
      id = 15996L,
      title = "None",
      picture = "https://nopic.com/img/null.jpg",
      punctuations = listOf()
    )
    val uiState = viewModel.uiState
    val catalogItem = uiState.value.successOr(emptyList()).find { it.id == expectedItem.id }
    assertNotNull(uiState)
    assertNull(catalogItem)
    assertNotEquals(expectedItem, catalogItem)
  }
}
