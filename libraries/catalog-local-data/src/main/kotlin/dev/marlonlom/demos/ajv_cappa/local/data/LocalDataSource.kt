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

package dev.marlonlom.demos.ajv_cappa.local.data

import kotlinx.coroutines.flow.Flow

/**
 *  Local data source concrete implementation class.
 *
 *  @author marlonlom
 */
interface LocalDataSource {

  fun getAllProducts(): Flow<List<ProductItem>>
  fun getProduct(productId: Long): Flow<ProductItem>
  fun getPunctuations(productId: Long): Flow<List<ProductItemPoint>>
  fun insertAllProducts(vararg products: ProductItem)
  fun insertAllPunctuations(vararg punctuations: ProductItemPoint)
  fun deleteAllProducts()
  fun deleteAllPunctuations()
}

/**
 * Local data source concrete implementation class.
 *
 * @author marlonlom
 *
 * @property appDatabase app database singleton instance.
 */
class LocalDataSourceImpl(
  private val appDatabase: AppDatabase
) : LocalDataSource {

  override fun getAllProducts() = appDatabase.catalogDao().getProducts()

  override fun getProduct(productId: Long) = appDatabase.catalogDao().getProduct(productId)

  override fun getPunctuations(productId: Long) = appDatabase.catalogDao().getPunctuations(productId)

  override fun insertAllProducts(vararg products: ProductItem) = appDatabase.catalogDao().insertAllProducts(*products)

  override fun insertAllPunctuations(vararg punctuations: ProductItemPoint) =
    appDatabase.catalogDao().insertAllPunctuations(*punctuations)

  override fun deleteAllProducts() = appDatabase.catalogDao().deleteAllProducts()

  override fun deleteAllPunctuations() = appDatabase.catalogDao().deleteAllPunctuations()

}
