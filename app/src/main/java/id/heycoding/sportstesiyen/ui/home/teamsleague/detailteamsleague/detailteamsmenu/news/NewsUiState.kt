package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news

sealed class NewsUiState {
    object Idle : NewsUiState()
    object Loading : NewsUiState()
    object Validate : NewsUiState()
    data class Success<T>(val data: T?) : NewsUiState()
    data class Error(val message: String?) : NewsUiState()
}