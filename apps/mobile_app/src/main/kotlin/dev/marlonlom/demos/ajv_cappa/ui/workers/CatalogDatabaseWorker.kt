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

package dev.marlonlom.demos.ajv_cappa.ui.workers

import android.content.Context
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dev.marlonlom.demos.ajv_cappa.BuildConfig
import dev.marlonlom.demos.ajv_cappa.R
import dev.marlonlom.demos.ajv_cappa.catalog.list.slug
import dev.marlonlom.demos.ajv_cappa.local.data.AppDatabase
import dev.marlonlom.demos.ajv_cappa.local.data.AppSetting
import dev.marlonlom.demos.ajv_cappa.local.data.LocalDataSource
import dev.marlonlom.demos.ajv_cappa.local.data.LocalDataSourceImpl
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItem
import dev.marlonlom.demos.ajv_cappa.local.data.ProductItemPoint
import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogDataService
import dev.marlonlom.demos.ajv_cappa.remote.data.successOr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatalogDatabaseWorker(
  appContext: Context,
  params: WorkerParameters
) : CoroutineWorker(appContext, params) {

  override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

    val catalogDataService = CatalogDataService()
    val catalogRemoteData = catalogDataService.fetchData().successOr(emptyList())

    if (catalogRemoteData.isEmpty()) Result.failure()

    val dbProductItems: List<ProductItem> = catalogRemoteData.map {
      ProductItem(
        id = it.id,
        title = it.title,
        slug = it.title.slug,
        titleNormalized = it.title.slug.replace("-", " "),
        picture = it.picture
      )
    }.distinctBy { it.slug }

    val dbProductItemPoints: List<ProductItemPoint> = catalogRemoteData
      .distinctBy { it.title.slug }
      .map { item ->
        item.punctuations.mapIndexed { index, punctuation ->
          ProductItemPoint(
            id = index.plus(1).toLong(),
            productId = item.id,
            label = punctuation.label,
            points = punctuation.pointsQty.toLong()
          )
        }
      }.flatten()

    val defaultSettings: List<AppSetting> = defaultSettings(applicationContext).let { map ->
      map.entries.map { entry -> AppSetting(entry.key, entry.value) }
    }

    val appDatabase = AppDatabase.getInstance(applicationContext)
    val localDataSource: LocalDataSource = LocalDataSourceImpl(appDatabase)
    with(localDataSource) {
      deleteAllPunctuations()
      deleteAllProducts()
      insertAllProducts(*dbProductItems.toTypedArray())
      insertAllPunctuations(*dbProductItemPoints.toTypedArray())
      insertAllSettings(*defaultSettings.toTypedArray())
    }

    Result.success()
  }

  private fun defaultSettings(applicationContext: Context) = hashMapOf(
    "app_version" to BuildConfig.VERSION_NAME,
    "dark_theme" to false.toString(),
    "dynamic_colors" to (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S).toString(),
    "privacy_policy" to applicationContext.getString(R.string.settings_link_privacy_policy),
    "terms_conditions" to applicationContext.getString(R.string.settings_link_privacy_policy),
    "personal_data_treatment_policy" to applicationContext.getString(R.string.settings_link_privacy_policy),
  )

  companion object {
    fun run(appContext: Context) {
      val work = OneTimeWorkRequestBuilder<CatalogDatabaseWorker>().build()
      WorkManager.getInstance(appContext).enqueue(work)
    }
  }

}
