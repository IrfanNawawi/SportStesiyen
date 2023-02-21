package id.heycoding.sportstesiyen.ui.home

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    object Validate : HomeUiState()
    data class Success<T>(val data: T?) : HomeUiState()
    data class Error(val message: String?) : HomeUiState()
}