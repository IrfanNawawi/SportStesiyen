package id.heycoding.sportstesiyen.ui.auth

sealed class AuthUiState {
    object Loading : AuthUiState()
    object Idle : AuthUiState()
    data class ValidateUser(val status: String) : AuthUiState()
    data class Success(val status: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}