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

import dev.marlonlom.demos.ajv_cappa.local.data.FakeLocalDataSource
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogDataService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class CatalogDetailRepositoryTest {

  private lateinit var repository: CatalogDetailRepository

  @Before
  fun setUp() {
    repository = CatalogDetailRepository(
      FakeLocalDataSource(
        CatalogDataService()
      )
    )
  }

  @Test
  fun shouldReturnSingleProductItem() = runTest {
    val expectedItem = ProductItem(
        id = 15396L,
        title = "Granizado",,,
        picture = "https://juanvaldez.com/wp-content/uploads/2022/10/Granizado-juan-Valdez.jpg",
    )
    val foundProduct = repository.find(expectedItem.id).first()
    Assert.assertNotNull(foundProduct)
    Assert.assertEquals(expectedItem, foundProduct!!.product)
    Assert.assertTrue(foundProduct.points.isNotEmpty())
  }

  @Test
  fun shouldReturnNullProductItem() = runTest {
    val foundProduct = repository.find(1L).first()
    Assert.assertNull(foundProduct)
  }

}
