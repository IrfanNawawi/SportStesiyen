package id.buaja.news.untils

import id.heycoding.sportstesiyen.utils.UiState
import retrofit2.Response

/**
 * Created By Julsapargi Nursam 6/3/20
 */

//fun <T : Any> fetchError(response: Response<T>): UiState.Error {
//    return when (response.code()) {
//        404 -> {
//            UiState.Error("Not Found")
//        }
//
//        401 -> {
//            UiState.Error("Auth")
//        }
//
//        in 500..599 -> {
//            UiState.Error("Server Kami Sedang Bermasalah")
//        }
//
//        else -> {
//            UiState.Error(response.message())
//        }
//    }
//}