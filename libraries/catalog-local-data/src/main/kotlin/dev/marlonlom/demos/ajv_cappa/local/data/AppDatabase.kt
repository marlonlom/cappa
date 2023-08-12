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
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ProductItem::class, ProductItemPoint::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

  abstract fun catalogDao(): CatalogDao

  companion object {
    @Volatile
    private var instance: AppDatabase? = null

    private const val DATABASE_NAME = "ajv-cappa-db"

    fun getInstance(context: Context, postCreateDatabaseAction: () -> Unit): AppDatabase {
      return instance ?: synchronized(this) {
        instance ?: buildDatabase(context, postCreateDatabaseAction).also { instance = it }
      }
    }

    private fun buildDatabase(
      context: Context,
      postCreateDatabaseAction: () -> Unit
    ): AppDatabase =
      Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .addCallback(callback = object : Callback() {
          override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            postCreateDatabaseAction()
          }
        }).build()
  }
}
