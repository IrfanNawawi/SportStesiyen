package id.heycoding.sportstesiyen.data.repository

import id.heycoding.sportstesiyen.data.entity.NewsSportResponse
import id.heycoding.sportstesiyen.data.service.NewsServices
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsServices: NewsServices
) : NewsRepository {
    override suspend fun getTopHeadlineNews(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ): Response<NewsSportResponse> {
        return newsServices.getTopHeadlineNewsSport(
            newsUrl = url,
            country = country,
            category = category,
            apiKey = apiKey
        )
    }

    override suspend fun getEverythingTeamsNews(
        url: String,
        query: String,
        language: String,
        sortBy: String,
        apiKey: String
    ): Response<NewsSportResponse> {
        return newsServices.getEverythingTeamsNewsSport(
            newsUrl = url,
            q = query,
            lang = language,
            sortBy = sortBy,
            apiKey = apiKey
        )
    }

}