package id.heycoding.sportstesiyen.domain.usecase

import id.buaja.news.untils.fetchError
import id.heycoding.sportstesiyen.data.repository.NewsRepository
import id.heycoding.sportstesiyen.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun getTopHeadlineNewsSportDataUseCase(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ) = flow {
        val response =
            newsRepository.getTopHeadlineNews(
                url = url,
                country = country,
                category = category,
                apiKey = apiKey
            )
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getEverythingTeamsNewsSportDataUseCase(
        url: String,
        query: String,
        language: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ) = flow {
        val response = newsRepository.getEverythingTeamsNews(
            url = url,
            query = query,
            language = language,
            from = from, to = to, sortBy = sortBy, apiKey = apiKey
        )
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }.flowOn(Dispatchers.IO)
}