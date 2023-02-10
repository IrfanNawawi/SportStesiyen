package id.heycoding.sportstesiyen.data.source

import id.heycoding.sportstesiyen.data.source.response.NewsSportResponse
import kotlinx.coroutines.flow.Flow

interface NewsDataSource {
    suspend fun getTopHeadlineNews(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ): NewsSportResponse

    suspend fun getEverythingTeamsNews(
        url: String,
        query: String,
        language: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): NewsSportResponse
}