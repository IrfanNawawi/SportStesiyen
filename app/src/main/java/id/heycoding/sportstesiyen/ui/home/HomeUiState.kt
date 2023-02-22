package id.heycoding.sportstesiyen.ui.home

import id.heycoding.sportstesiyen.domain.model.Event
import id.heycoding.sportstesiyen.domain.model.News
import id.heycoding.sportstesiyen.domain.model.Teams

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    object Validate : HomeUiState()
    data class SuccessEvents(val data: List<Event>) : HomeUiState()
    data class SuccessNews(val data: List<News>) : HomeUiState()
    data class SuccessTeams(val data: List<Teams>) : HomeUiState()
    data class Error(val message: String?) : HomeUiState()
}