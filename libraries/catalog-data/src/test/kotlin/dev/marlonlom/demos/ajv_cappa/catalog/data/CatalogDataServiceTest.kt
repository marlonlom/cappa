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

package dev.marlonlom.demos.ajv_cappa.catalog.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class CatalogDataServiceTest {

  private lateinit var catalogResponse: Response<List<CatalogItem>>

  @Before
  fun init() {
    catalogResponse = CatalogDataService().fetchData()
  }

  @Test
  fun shouldValidateCatalogDataIsNotEmpty() {
    val list = catalogResponse.successOr(emptyList())
    assertTrue(catalogResponse is Response.Success)
    assertTrue(list.isNotEmpty())
    assertEquals(62, list.size)
  }

  @Test
  fun shouldValidateExpectedCatalogItemExist() {
    val expectedItem = CatalogItem(
      id = 15396L,
      title = "Granizado",
      picture = "https://juanvaldez.com/wp-content/uploads/2022/10/Granizado-juan-Valdez.jpg",
      punctuations = listOf(
        Punctuations(
          label = "Mediano",
          pointsQty = 2225
        )
      )
    )
    val list = catalogResponse.successOr(emptyList())
    val foundItem = list.single { it.id == 15396L }
    assertTrue(catalogResponse is Response.Success)
    assertNotNull(foundItem)
    assertEquals(expectedItem, foundItem)
  }

  @Test
  fun shouldValidateExpectedCatalogItemNotExist() {
    val expectedItem = CatalogItem(
      id = 15396L,
      title = "None",
      picture = "https://nopic.com/img/null.jpg",
      punctuations = listOf()
    )
    val list = catalogResponse.successOr(emptyList())
    val foundItem = list.single { it.id == 15396L }
    assertNotNull(foundItem)
    assertNotEquals(expectedItem, foundItem)
  }

  @Test
  fun shouldValidateErrorFetchingWrongJsonData() {
    val service = CatalogDataService()
    service.changePath("none.json")
    catalogResponse = service.fetchData()
    assertTrue(catalogResponse is Response.Failure)
    assertNotNull(catalogResponse.failure())
    assertTrue(catalogResponse.failure() is CatalogDataNotFoundException)
  }

  @Test
  fun shouldValidateErrorWhileSerializingJsonData() {
    val service = CatalogDataService()
    service.changePath("catalog-single.json")
    catalogResponse = service.fetchData()
    assertTrue(catalogResponse is Response.Failure)
    assertNotNull(catalogResponse.failure())
    assertTrue(catalogResponse.failure() is CatalogDataSerializationException)
  }

}
