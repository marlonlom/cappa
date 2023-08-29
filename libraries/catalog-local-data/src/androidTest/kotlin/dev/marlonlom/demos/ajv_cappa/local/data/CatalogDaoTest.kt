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

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CatalogDaoTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var database: AppDatabase
  private lateinit var dao: CatalogDao

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    dao = database.catalogDao()
  }

  @After
  fun teardown() {
    database.close()
  }

  @Test
  fun shouldInsertProductItem() = runBlocking {
    val entity = ProductItem(
      id = 1L,
      title = "Pod",
      slug = "pod",
      titleNormalized = "pod",
      picture = "https://noimage.no.com/no.png"
    )
    dao.insertAllProducts(entity)
    val list = dao.getProducts().first()
    assertThat(list).contains(entity)
  }


  @Test
  fun shouldInsertThenDeleteAllProductItems() = runBlocking {
    val entity = ProductItem(
      id = 1L,
      title = "Pod",
      slug = "pod",
      titleNormalized = "pod",
      picture = "https://noimage.no.com/no.png"
    )
    dao.insertAllProducts(entity)
    dao.deleteAllProducts()
    val list = dao.getProducts().first()
    assertThat(list).isEmpty()
  }

  @Test
  fun shouldInsertProductItemPoint() = runBlocking {
    val entity = ProductItemPoint(11L, 1L, "Unidad", 1234L)
    dao.insertAllPunctuations(entity)
    val list = dao.getPunctuations(1L).first()
    assertThat(list).contains(entity)
  }

  @Test
  fun shouldInsertThenDeleteAllProductItemPoints() = runBlocking {
    val entity = ProductItemPoint(11L, 1L, "Unidad", 1234L)
    dao.insertAllPunctuations(entity)
    dao.deleteAllPunctuations()
    val list = dao.getPunctuations(1L).first()
    assertThat(list).isEmpty()
  }

  @Test
  fun shouldInsertThenQueryDetailedProductItem() = runBlocking {
    val product = ProductItem(
      id = 1L,
      title = "Pod",
      slug = "pod",
      titleNormalized = "pod",
      picture = "https://noimage.no.com/no.png"
    )
    val point = ProductItemPoint(11L, 1L, "Unidad", 1234L)

    dao.insertAllProducts(product)
    dao.insertAllPunctuations(point)

    val found = dao.getProduct(1L).first()
    val points = dao.getPunctuations(1L).first()

    assertThat(found).isNotNull()
    assertThat(found).isEqualTo(product)
    assertThat(points).isNotNull()
    assertThat(points).isNotEmpty()
    assertThat(points).contains(point)
  }

  @Test
  fun shouldSuccessfullySearchProductsByText() = runBlocking {
    val items = "Morbi,Accumsan,Aliquet,Urna,Nulla".split(",").mapIndexed { index, txt ->
      ProductItem(
        id = (index + 1).toLong(),
        title = txt,
        slug = txt.lowercase(),
        titleNormalized = txt.lowercase(),
        picture = "https://images.com/${txt.lowercase()}"
      )
    }

    val pointsPerItem = items
      .map { it.id }
      .mapIndexed { index, itemId ->
        ProductItemPoint((index + 1).toLong(), itemId, "Unidad", 100L)
      }

    dao.insertAllProducts(*items.toTypedArray())
    dao.insertAllPunctuations(*pointsPerItem.toTypedArray())

    val searchProducts = dao.searchProducts(items[2].title.lowercase()).first()

    assertThat(searchProducts).isNotEmpty()
    assertThat(searchProducts).contains(items[2])
  }

  @Test
  fun shouldReturnEmptyResultsInSearchProductsByText() = runBlocking {
    val items = "Morbi,Accumsan,Aliquet,Urna,Nulla".split(",").mapIndexed { index, txt ->
      ProductItem(
        id = (index + 1).toLong(),
        title = txt,
        slug = txt.lowercase(),
        titleNormalized = txt.lowercase(),
        picture = "https://images.com/${txt.lowercase()}"
      )
    }

    val pointsPerItem = items
      .map { it.id }
      .mapIndexed { index, itemId ->
        ProductItemPoint((index + 1).toLong(), itemId, "Unidad", 100L)
      }

    dao.insertAllProducts(*items.toTypedArray())
    dao.insertAllPunctuations(*pointsPerItem.toTypedArray())

    val searchProducts = dao.searchProducts("Nulia".lowercase()).first()

    assertThat(searchProducts).isEmpty()
  }
}
