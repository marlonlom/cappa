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

import dev.marlonlom.demos.ajv_cappa.catalog.list.slug
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
    return flowOf(listResponse.map {
      ProductItem(
        id = it.id,
        title = it.title,
        slug = it.title.slug,
        titleNormalized = it.title.lowercase(),
        picture = it.picture
      )
    })
  }

  override fun getProduct(productId: Long): Flow<ProductItem> {
    val listResponse: ProductItem = remoteDataService.fetchData()
      .successOr(emptyList())
      .find { it.id == productId }
      .let {
        if (it != null) ProductItem(
          id = it.id,
          title = it.title,
          slug = it.title.slug,
          titleNormalized = it.title.lowercase(),
          picture = it.picture
        ) else NONE
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

  override fun searchProducts(search: String?): Flow<List<ProductItem>> {
    if (search.isNullOrEmpty()) return flowOf(emptyList())

    val listResponse = remoteDataService.fetchData()
      .successOr(emptyList())
      .filter { it.title.lowercase().indexOf(search.lowercase()) >= 0 }
      .map {
        ProductItem(
          id = it.id,
          title = it.title,
          slug = it.title.slug,
          titleNormalized = it.title.lowercase(),
          picture = it.picture
        )
      }

    return flowOf(listResponse)

  }

  override fun insertAllSettings(vararg appSettings: AppSetting) {
    TODO("Not yet implemented")
  }

  override fun getAppSettings(): Flow<List<AppSetting>> =
    flowOf(settings.let { map ->
      map.entries.map { entry -> AppSetting(entry.key, entry.value) }
    })

  companion object {
    val NONE = ProductItem(-1, "", "", "", "")

    val settings = hashMapOf(
      "app_version" to "1.0.0",
      "dark_theme" to false.toString(),
      "dynamic_colors" to false.toString(),
      "privacy_policy" to "https://example.org/app/privacy_policy",
      "terms_conditions" to "https://example.org/app/terms_conditions",
      "personal_data_treatment_policy" to "https://example.org/app/personal_data_treatment_policy"
    )
  }
}
