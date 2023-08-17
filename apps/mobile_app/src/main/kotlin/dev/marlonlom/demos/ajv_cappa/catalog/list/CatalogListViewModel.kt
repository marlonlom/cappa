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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.marlonlom.demos.ajv_cappa.catalog.list.CatalogListState.Loading
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class CatalogListState {
  object Loading : CatalogListState()
  object Empty : CatalogListState()
  data class Listing(val list: List<ProductItem>) : CatalogListState()
}

/**
 * Catalog list view model class.
 *
 * @author marlonlom
 *
 * @property repository catalog list repository dependency
 */
class CatalogListViewModel(
  private val repository: CatalogListRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<CatalogListState>(Loading)

  /**
   * UI state object for view model
   */
  val uiState = _uiState.stateIn(
    viewModelScope,
    SharingStarted.Eagerly,
    Loading
  )

  init {
    Timber.d("[CatalogListViewModel] started fetching catalog list")
    fetchList()
  }

  private fun fetchList() {
    viewModelScope.launch {
      /*_uiState.value = CatalogListState.Empty*/

      val products = repository.getAllProducts()
      products.collect {
        _uiState.value = when {
          it.isEmpty() -> CatalogListState.Empty
          else -> CatalogListState.Listing(it)
        }

      }
    }
  }

  companion object {
    fun factory(
      repository: CatalogListRepository
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatalogListViewModel(repository) as T
      }
    }
  }
}
