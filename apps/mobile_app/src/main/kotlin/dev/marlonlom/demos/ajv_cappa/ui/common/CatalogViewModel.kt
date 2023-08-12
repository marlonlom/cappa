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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItemPoint
import dev.marlonlom.demos.ajv_cappa.ui.common.CatalogUiState.Home
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber


class CatalogViewModel(
  private val repository: CatalogRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)

  val uiState = _uiState.stateIn(
    viewModelScope,
    SharingStarted.Eagerly,
    _uiState.value
  )

  init {
    fetchInitialInformation()
  }

  private fun fetchInitialInformation() {
    viewModelScope.launch {
      val response = repository.getAllProducts()
      response.collect { items ->
        _uiState.update { Home(items, NEGATIVE_ITEM_ID) }
      }
    }
  }

  /**
   * Updates selected item id for success ui state.
   *
   * @param itemId selected item id from list
   */
  fun updateSelectedItemId(itemId: Long) {
    viewModelScope.launch {
      if (_uiState.value is Home) {
        val newStateValue = (_uiState.value as Home).copy(
          selectedId = itemId
        )
        Timber.d("[CatalogViewModel] newStateValue.selectedId=${newStateValue.selectedId}")
        _uiState.update {
          newStateValue
        }
      }
    }
  }

  /**
   * Returns a single product item using selected id from success ui state
   *
   * @return found product item, null otherwise
   */
  fun fetchSingle(): Flow<ProductItem> = when (_uiState.value) {
    is Home -> repository.getProduct((_uiState.value as Home).selectedId)
    else -> flowOf(NONE)
  }

  /**
   * Returns a list of points for selected product item
   *
   * @return points list for product item
   */
  fun fetchPoints(): Flow<List<ProductItemPoint>> = when (_uiState.value) {
    is Home -> repository.getPunctuations((_uiState.value as Home).selectedId)
    else -> flowOf(emptyList())
  }

  /**
   * Factory for HomeViewModel that takes CatalogDataService as a dependency
   */
  companion object {

    const val NEGATIVE_ITEM_ID = -1L

    val NONE = ProductItem(-1, "", "")

    fun provideFactory(
      repository: CatalogRepository
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatalogViewModel(repository) as T
      }
    }
  }
}
