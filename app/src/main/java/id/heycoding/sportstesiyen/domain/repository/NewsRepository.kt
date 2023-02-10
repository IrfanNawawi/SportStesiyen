package id.heycoding.sportstesiyen.domain.repository

import id.heycoding.sportstesiyen.data.source.response.NewsSportResponse
import id.heycoding.sportstesiyen.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlineNews(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ): Flow<List<News>>

    fun getEverythingTeamsNews(
        url: String,
        query: String,
        language: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): Flow<List<News>>
}