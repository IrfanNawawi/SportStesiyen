package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey

import id.heycoding.sportstesiyen.domain.model.Jersey

sealed class JerseyUiState {
    object Idle : JerseyUiState()
    object Loading : JerseyUiState()
    data class Success<T>(val data: List<T>) : JerseyUiState()
    data class Error(val message: String?) : JerseyUiState()
}