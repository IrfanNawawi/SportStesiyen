package id.heycoding.sportstesiyen.ui.statistic

import id.heycoding.sportstesiyen.domain.model.Leagues
import id.heycoding.sportstesiyen.domain.model.Seasons
import id.heycoding.sportstesiyen.domain.model.Statistic

sealed class StatisticUiState {
    object Idle : StatisticUiState()
    object Loading : StatisticUiState()
    object Validate : StatisticUiState()
    data class SuccessLeague(val data: List<Leagues>) : StatisticUiState()
    data class SuccessSeasons(val data: List<Seasons>) : StatisticUiState()
    data class SuccessStatistic(val data: List<Statistic>) : StatisticUiState()
    data class Error(val message: String?) : StatisticUiState()
}