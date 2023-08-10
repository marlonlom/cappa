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

package dev.marlonlom.demos.ajv_cappa.main.data

import kotlinx.serialization.Serializable

/**
 * Catalog item data class.
 *
 * @author marlonlom
 *
 * @property id item id as number
 * @property title item title as text
 * @property picture item picture url
 * @property punctuations list of punctuation
 */
@Serializable
data class CatalogItem(
  val id: Long,
  val title: String,
  val picture: String,
  val punctuations: List<Punctuation>
)

/**
 * Punctuation data class.
 *
 * @author marlonlom
 *
 * @property label punctuation label text
 * @property pointsQty punctuation quantity value
 */
@Serializable
data class Punctuation(
  val label: String,
  val pointsQty: Int
)


/**
 * A generic class that holds a value or an exception
 *
 * @author marlonlom
 *
 * @param R
 */
sealed class Response<out R> {
  data class Success<out T>(val data: T) : Response<T>()
  data class Failure(val exception: Exception) : Response<Nothing>()
}

/**
 * inline function that returns the success state value or a default value.
 *
 * @author marlonlom
 *
 * @param T
 * @param fallback default result value
 * @return success state value or a fallback value
 */
fun <T> Response<T>.successOr(fallback: T): T {
  return (this as? Response.Success<T>)?.data ?: fallback
}

/**
 * inline function that returns the detailed exception for failure state
 *
 * @author marlonlom
 *
 * @param T
 * @return failure state value
 */
fun <T> Response<T>.failure(): Exception = (this as? Response.Failure)?.exception ?: Exception()
