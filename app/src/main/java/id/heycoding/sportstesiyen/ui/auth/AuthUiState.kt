package id.heycoding.sportstesiyen.ui.auth

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState

    data class Success(val data: Boolean) : AuthUiState
    data class Error(val error: String) : AuthUiState
}