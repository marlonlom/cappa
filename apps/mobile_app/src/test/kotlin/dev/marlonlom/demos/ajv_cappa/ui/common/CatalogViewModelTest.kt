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

package dev.marlonlom.demos.ajv_cappa.ui.common

import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogDataService
import dev.marlonlom.demos.ajv_cappa.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class CatalogViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var viewModel: CatalogViewModel

  @Before
  fun setUp() {
    viewModel = CatalogViewModel(
      CatalogRepository(
        FakeLocalDataSource(
          CatalogDataService()
        )
      )
    )
  }

  @Test
  fun shouldCheckUiStateValueIsNotEmptyList() = runTest {
    val uiState = viewModel.uiState
    assertNotNull(uiState)
    when (uiState.value) {
      is CatalogUiState.Home -> {
        val home = uiState.value as CatalogUiState.Home
        assertTrue(home.list.isNotEmpty())
        assertEquals(-1L, home.selectedId)
      }

      else -> fail()
    }
  }

  @Test
  fun shouldCheckUiStateSingleValueExists() = runTest {
    val expectedItem = ProductItem(
      id = 15396L,
      title = "Granizado",
      picture = "https://juanvaldez.com/wp-content/uploads/2022/10/Granizado-juan-Valdez.jpg",
    )

    val uiState = viewModel.uiState
    assertNotNull(uiState)
    when (uiState.value) {
      is CatalogUiState.Home -> {
        val home = uiState.value as CatalogUiState.Home
        viewModel.updateSelectedItemId(expectedItem.id)
        val catalogItem = viewModel.fetchSingle().first()
        assertTrue(home.list.isNotEmpty())
        assertNotNull(catalogItem)
        assertEquals(expectedItem, catalogItem)
      }

      else -> fail()
    }
  }

  @Test
  fun shouldValidateExpectedCatalogItemNotExist() = runTest {
    val expectedItem = ProductItem(
      id = 15996L,
      title = "Loading",
      picture = "https://nopic.com/img/null.jpg"
    )
    val uiState = viewModel.uiState
    assertNotNull(uiState)
    when (uiState.value) {
      is CatalogUiState.Home -> {
        val home = uiState.value as CatalogUiState.Home
        viewModel.updateSelectedItemId(expectedItem.id)
        val catalogItem = viewModel.fetchSingle().first()
        assertTrue(home.list.isNotEmpty())
        assertNotNull(catalogItem)
        assertEquals(FakeLocalDataSource.NONE, catalogItem)
        assertNotEquals(expectedItem, catalogItem)
      }

      else -> fail()
    }
  }
}
