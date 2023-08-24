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

package  dev.marlonlom.demos.ajv_cappa.local.data

import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogDataService
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogItem
import dev.marlonlom.demos.ajv_cappa.remote.data.successOr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class FakeLocalDataSource(
  private val remoteDataService: CatalogDataService
) : LocalDataSource {

  override fun getAllProducts(): Flow<List<ProductItem>> {
    val listResponse = remoteDataService.fetchData().successOr(emptyList())
    return flowOf(listResponse.map { ProductItem(
        id = it.id,
        title = it.title,,,
        picture = it.picture
    ) })
  }

  override fun getProduct(productId: Long): Flow<ProductItem> {
    val listResponse: ProductItem = remoteDataService.fetchData()
      .successOr(emptyList())
      .find { it.id == productId }
      .let {
        if (it != null) ProductItem(it.id, it.title,,, it.picture) else NONE
      }
    return flowOf(listResponse)
  }

  override fun getPunctuations(productId: Long): Flow<List<ProductItemPoint>> {
    val listResponse: CatalogItem = remoteDataService.fetchData()
      .successOr(emptyList())
      .find { it.id == productId } ?: return flowOf(emptyList())

    val units = listResponse.punctuations.mapIndexed { index, punctuation ->
      ProductItemPoint(
        id = index.plus(1).toLong(),
        productId = listResponse.id,
        label = punctuation.label,
        points = punctuation.pointsQty.toLong()
      )
    }
    return flowOf(units)
  }

  override fun insertAllProducts(vararg products: ProductItem) {
    TODO("Not yet implemented")
  }

  override fun insertAllPunctuations(vararg punctuations: ProductItemPoint) {
    TODO("Not yet implemented")
  }

  override fun deleteAllProducts() {
    TODO("Not yet implemented")
  }

  override fun deleteAllPunctuations() {
    TODO("Not yet implemented")
  }

  companion object {
    val NONE = ProductItem(-1, "",,, "")
  }
}
