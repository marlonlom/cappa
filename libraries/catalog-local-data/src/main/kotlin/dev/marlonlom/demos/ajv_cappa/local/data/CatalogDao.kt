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

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Entity model data class for Catalog product item.
 *
 * @property id Product item id.
 * @property title Product item title.
 * @property picture Product item picture.
 */
@Entity(tableName = "cappa_product")
data class ProductItem(
  @ColumnInfo(name = "product_id") @PrimaryKey() val id: Long,
  @ColumnInfo(name = "product_title") val title: String,
  @ColumnInfo(name = "product_picture") val picture: String
)

/**
 * Entity model data class for product item point aka punctuation.
 *
 * @property id Punctuation id.
 * @property productId Punctuation product item id.
 * @property label Punctuation title.
 * @property points Punctuation points numeric value.
 */
@Entity(
  tableName = "cappa_punctuation",
  primaryKeys = ["punctuation_id", "punctuation_product_id"]
)
data class ProductItemPoint(
  @ColumnInfo(name = "punctuation_id") val id: Long,
  @ColumnInfo(name = "punctuation_product_id") val productId: Long,
  @ColumnInfo(name = "punctuation_label") val label: String,
  @ColumnInfo(name = "punctuation_points") val points: Long
)

/**
 * Catalog data access object interface definition.
 *
 * @author marlonlom
 *
 */
@Dao
interface CatalogDao {

  /**
   * Query for retrieving product items list.
   *
   * @return Product items list, or empty list, ><as Flow.
   */
  @Query("SELECT * FROM cappa_product")
  fun getProducts(): Flow<List<ProductItem>>

  /**
   * Query for retrieving single product item using its id.
   *
   * @param productId Product item id.
   * @return Found product item, or null.
   */
  @Query("SELECT * FROM cappa_product WHERE product_id = :productId ")
  fun getProduct(productId: Long): Flow<ProductItem>

  /**
   * Query for retrieving product item points list, aka punctuations list.
   *
   * @param productId Product item id.
   * @return Found product item points list, or empty list.
   */
  @Query("SELECT * FROM cappa_punctuation WHERE punctuation_product_id = :productId ")
  fun getPunctuations(productId: Long): Flow<List<ProductItemPoint>>

  /**
   * Upsert product items.
   *
   * @param products product items.
   */
  @Upsert()
  fun insertAllProducts(vararg products: ProductItem)

  /**
   * Upsert product item punctuations.
   *
   * @param punctuations product item points.
   */
  @Upsert()
  fun insertAllPunctuations(vararg punctuations: ProductItemPoint)

  /**
   * Deletes all product items in local storage.
   */
  @Query("DELETE FROM cappa_product")
  fun deleteAllProducts()

  /**
   * Deletes all product item points in local storage.
   */
  @Query("DELETE FROM cappa_punctuation")
  fun deleteAllPunctuations()

  /**
   * Perform search of product items using provided query text.
   *
   * @param search Query text.
   * @return Product items list, or empty list, as Flow.
   */
  @Query("SELECT * FROM cappa_product WHERE lower(product_title) LIKE :search")
  fun searchProducts(search: String?): Flow<List<ProductItem>>

}
