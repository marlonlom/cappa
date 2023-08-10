package dev.marlonlom.demos.ajv_cappa.ui.common

import dev.marlonlom.demos.ajv_cappa.remote.data.CatalogItem

/**
 * Home ui state class definition
 *
 * @author marlonlom
 *
 */
sealed class CatalogUiState {

  /**
   * Success ui state data class.
   *
   * @author marlonlom
   *
   * @property list catalog items list
   * @property selectedId selected item id from list
   */
  data class Home(
    val list: List<CatalogItem>,
    val selectedId: Long
  ) : CatalogUiState()

  /**
   * Error ui state data class.
   *
   * @author marlonlom
   *
   * @property exception related exception.
   *
   */
  data class Error(
    val exception: Exception
  ) : CatalogUiState()

  /**
   * Object definition for initial loading ui state.
   * @author marlonlom
   */
  object Loading : CatalogUiState()
}
