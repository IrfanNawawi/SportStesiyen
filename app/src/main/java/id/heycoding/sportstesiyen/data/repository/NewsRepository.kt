package id.heycoding.sportstesiyen.data.repository

import id.heycoding.sportstesiyen.data.entity.NewsSportResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getTopHeadlineNews(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ): Response<NewsSportResponse>

    suspend fun getEverythingTeamsNews(
        url: String,
        query: String,
        language: String,
        sortBy: String,
        apiKey: String
    ): Response<NewsSportResponse>
}