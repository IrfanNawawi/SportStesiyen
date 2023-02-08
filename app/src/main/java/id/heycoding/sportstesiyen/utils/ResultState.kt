package id.heycoding.sportstesiyen.utils

sealed class ResultState<out T : Any> {
    data class Message(val message: String) : ResultState<String>()
    data class Success<out T : Any>(val data: T?) : ResultState<T>()
}