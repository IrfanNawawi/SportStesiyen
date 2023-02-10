package id.heycoding.sportstesiyen.domain.usecase

import id.heycoding.sportstesiyen.domain.model.News
import id.heycoding.sportstesiyen.domain.model.Teams
import id.heycoding.sportstesiyen.domain.repository.NewsRepository
import id.heycoding.sportstesiyen.domain.repository.SoccerRepository
import kotlinx.coroutines.flow.Flow

class NewsUseCase(private val newsRepository: NewsRepository) {
    fun getTopHeadlineNewsSportDataUseCase(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ): Flow<List<News>> {
        return newsRepository.getTopHeadlineNews(
            url = url,
            country = country,
            category = category,
            apiKey = apiKey
        )
    }

    fun getEverythingTeamsNewsSportDataUseCase(
        url: String,
        query: String,
        language: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): Flow<List<News>> {
        return newsRepository.getEverythingTeamsNews(
            url = url,
            query = query,
            language = language,
            from = from, to = to, sortBy = sortBy, apiKey = apiKey
        )
    }
}