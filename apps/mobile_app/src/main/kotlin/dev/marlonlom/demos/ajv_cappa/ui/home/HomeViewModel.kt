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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marlonlom.demos.ajv_cappa.catalog.data.CatalogDataService
import dev.marlonlom.demos.ajv_cappa.catalog.data.CatalogItem
import dev.marlonlom.demos.ajv_cappa.catalog.data.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
  private val dataService: CatalogDataService
) : ViewModel() {

  private val viewModelState = MutableStateFlow<Response<List<CatalogItem>>>(Response.Success(emptyList()))

  val uiState = viewModelState.stateIn(
    viewModelScope,
    SharingStarted.Eagerly,
    viewModelState.value
  )

  init {
    fetchCatalogItems()
  }

  private fun fetchCatalogItems() {
    viewModelScope.launch {
      val response: Response<List<CatalogItem>> = dataService.fetchData()
      viewModelState.update {
        response
      }
    }
  }
}
