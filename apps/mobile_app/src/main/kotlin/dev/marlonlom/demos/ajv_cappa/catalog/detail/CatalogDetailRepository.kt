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

package dev.marlonlom.demos.ajv_cappa.catalog.detail

import dev.marlonlom.demos.ajv_cappa.local.data.LocalDataSource
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItemPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Data class definition for catalog item detail.
 *
 * @property product product detail
 * @property points product points list
 */
data class CatalogDetail(
  val product: ProductItem,
  val points: List<ProductItemPoint>
)

/**
 * Catalog details repository class.
 *
 * @property localDataSource local data source dependency
 * @property coroutineDispatcher coroutine dispatcher
 */
class CatalogDetailRepository(
  private val localDataSource: LocalDataSource,
  private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

  fun find(itemId: Long): Flow<CatalogDetail?> {
    coroutineDispatcher.run {
      return combine(
        localDataSource.getProduct(itemId),
        localDataSource.getPunctuations(itemId)
      ) { product, points ->
        if (product.id == -1L) {
          null
        } else {
          CatalogDetail(product, points)
        }
      }
    }
  }

}
