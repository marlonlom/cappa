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
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "cappa_product")
data class ProductItem(
  @ColumnInfo(name = "product_id") @PrimaryKey() val id: Long,
  @ColumnInfo(name = "product_title") val title: String,
  @ColumnInfo(name = "product_picture") val picture: String
)

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

@Dao
interface CatalogDao {

  @Query("SELECT * FROM cappa_product")
  fun getProducts(): Flow<List<ProductItem>>

  @Query("SELECT * FROM cappa_product WHERE product_id = :productId ")
  fun getProduct(productId: Long): Flow<ProductItem>

  @Query("SELECT * FROM cappa_punctuation WHERE punctuation_product_id = :productId ")
  fun getPunctuations(productId: Long): Flow<List<ProductItemPoint>>

  @Insert()
  fun insertAllProducts(vararg products: ProductItem)

  @Insert()
  fun insertAllPunctuations(vararg punctuations: ProductItemPoint)

  @Query("DELETE FROM cappa_product")
  fun deleteAllProducts()

  @Query("DELETE FROM cappa_punctuation")
  fun deleteAllPunctuations()

}
