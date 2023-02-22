package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news

import id.heycoding.sportstesiyen.domain.model.News

sealed class NewsUiState {
    object Idle : NewsUiState()
    object Loading : NewsUiState()
    object Validate : NewsUiState()
    data class Success(val data: List<News>) : NewsUiState()
    data class Error(val message: String?) : NewsUiState()
}