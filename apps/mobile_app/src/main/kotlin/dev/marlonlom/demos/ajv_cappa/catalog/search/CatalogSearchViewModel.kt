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

package dev.marlonlom.demos.ajv_cappa.catalog.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.marlonlom.demos.ajv_cappa.catalog.search.CatalogSearchState.EmptyResults
import dev.marlonlom.demos.ajv_cappa.catalog.search.CatalogSearchState.Ready
import dev.marlonlom.demos.ajv_cappa.catalog.search.CatalogSearchState.WithResults
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Catalog search view model class.
 *
 * @author marlonlom
 *
 * @property repository catalog list repository dependency
 */
class CatalogSearchViewModel(
  private val repository: CatalogSearchRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<CatalogSearchState>(searchReadyState)

  /**
   * UI state object for view model
   */
  val uiState = _uiState.stateIn(
    viewModelScope,
    SharingStarted.Eagerly,
    searchReadyState
  )

  /**
   * Performs search using provided text.
   *
   * @param searchText search text
   */
  suspend fun doSearch(searchText: String) {
    if (searchText.isEmpty()) return
    _uiState.value = CatalogSearchState.Searching(searchText)
    viewModelScope.launch {
      delay(1_000)
      repository.searchProducts("%%$searchText%").collect { itemList ->
        _uiState.value = when {
          itemList.isNotEmpty() -> WithResults(searchText, itemList)
          else -> EmptyResults(searchText)
        }
      }
    }
  }

  fun performClearing() {
    viewModelScope.launch {
      _uiState.update {
        Ready()
      }
    }
  }

  companion object {

    val searchReadyState = Ready()

    fun factory(
      repository: CatalogSearchRepository
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatalogSearchViewModel(repository) as T
      }
    }
  }

}

/**
 * Catalog search ui state sealed class.
 *
 * @author marlonlom
 *
 */
sealed interface CatalogSearchState {

  /**
   * Search input text.
   */
  val searchInput: String

  /**
   * Data class for initial search state.
   *
   * @property searchInput
   */
  data class Ready(
    override val searchInput: String = ""
  ) : CatalogSearchState

  /**
   * Data class for Loading catalog search ui state.
   *
   * @property searchInput
   */
  data class Searching(
    override val searchInput: String
  ) : CatalogSearchState

  /**
   * Data class for Empty results from catalog search ui state.
   *
   * @property searchInput Search text input
   */
  data class EmptyResults(
    override val searchInput: String
  ) : CatalogSearchState

  /**
   * Data class for Catalog search results ui state with non empty list.
   *
   * @property searchInput Search text input
   * @property list Search results list
   */
  data class WithResults(
    override val searchInput: String,
    val list: List<ProductItem>
  ) : CatalogSearchState
}
