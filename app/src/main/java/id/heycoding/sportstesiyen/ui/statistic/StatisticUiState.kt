package id.heycoding.sportstesiyen.ui.statistic

sealed class StatisticUiState {
    object Idle : StatisticUiState()
    object Loading : StatisticUiState()
    object Validate : StatisticUiState()
    data class Success<T>(val data: T?) : StatisticUiState()
    data class Error(val message: String?) : StatisticUiState()
}