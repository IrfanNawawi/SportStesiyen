package id.heycoding.sportstesiyen.utils

sealed class UiState<out T : Any> {
    data class Message(val message: String) : UiState<String>()
    data class Success<out T : Any>(val data: T?) : UiState<T>()
}
