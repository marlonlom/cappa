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

package dev.marlonlom.demos.ajv_cappa.ui.main

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

object AppScaffoldUtil {

  fun canShowTopBar(
    wsc: WindowSizeClass, isDetailDestination: Boolean
  ) = isDetailDestination and !isTabletLandscape(wsc)

  fun canShowNavigationRail(
    wsc: WindowSizeClass, isDetailDestination: Boolean
  ) = isMobileLandscape(wsc) and !isDetailDestination or isTabletLandscape(wsc)

  fun canShowBottomBar(windowSizeClass: WindowSizeClass, isDetailDestination: Boolean) =
    !isDetailDestination && !windowSizeClass.isExpandedWidth

  fun isTabletLandscape(wsc: WindowSizeClass) = wsc.isExpandedWidth and wsc.isMediumHeight

  fun isMobileLandscape(wsc: WindowSizeClass) = wsc.isExpandedWidth and wsc.isCompactHeight
}

val WindowSizeClass.isCompactHeight: Boolean
  get() = this.heightSizeClass == WindowHeightSizeClass.Compact

val WindowSizeClass.isMediumHeight: Boolean
  get() = this.heightSizeClass == WindowHeightSizeClass.Medium

val WindowSizeClass.isExpandedWidth: Boolean
  get() = this.widthSizeClass == WindowWidthSizeClass.Expanded

val WindowSizeClass.isCompactWidth: Boolean
  get() = this.widthSizeClass == WindowWidthSizeClass.Compact
