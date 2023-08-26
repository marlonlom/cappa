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

  /**
   * Returns all items catalog list.
   *
   * @return Catalog list
   */
  fun getAllProducts(): Flow<List<ProductItem>>

  /**
   * Return a single product with provided id as Flow.
   *
   * @param productId Product id.
   * @return Single product, or null.
   */
  fun getProduct(productId: Long): Flow<ProductItem>

  /**
   * Return punctuations list for a single product using its id.
   *
   * @param productId Product id.
   * @return Punctuations list, or empty list.
   */
  fun getPunctuations(productId: Long): Flow<List<ProductItemPoint>>

  /**
   * Insert all product items.
   *
   * @param products Product items as typed array.
   */
  fun insertAllProducts(vararg products: ProductItem)

  /**
   * Insert all punctuations.
   *
   * @param punctuations Punctuation items as typed array.
   */
  fun insertAllPunctuations(vararg punctuations: ProductItemPoint)

  /**
   * Delete all products.
   */
  fun deleteAllProducts()

  /**
   * Delete all punctuations.
   */
  fun deleteAllPunctuations()

  /**
   * Search product items using provided text.
   *
   * @param search Input text for search.
   * @return
   */
  fun searchProducts(search: String?): Flow<List<ProductItem>>

  /**
   * Insert all application settings.
   *
   * @param appSettings Application setting items as typed array.
   */
  fun insertAllSettings(vararg appSettings: AppSetting)

  /**
   * Returns application settings.
   *
   * @return Application settings list, or empty list, as Flow.
   */
  fun getAppSettings(): Flow<List<AppSetting>>

  /**
   * Updates boolean setting value for selected key.
   *
   * @param setting Instance of setting for be updated.
   */
  fun updateBooleanSetting(setting: AppSetting)
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
  override fun searchProducts(search: String?) = appDatabase.catalogDao().searchProducts(search)
  override fun insertAllSettings(vararg appSettings: AppSetting) =
    appDatabase.catalogDao().insertAllSettings(*appSettings)

  override fun getAppSettings(): Flow<List<AppSetting>> = appDatabase.catalogDao().getSettings()
  override fun updateBooleanSetting(setting: AppSetting) = appDatabase.catalogDao().insertAllSettings(setting)

}
