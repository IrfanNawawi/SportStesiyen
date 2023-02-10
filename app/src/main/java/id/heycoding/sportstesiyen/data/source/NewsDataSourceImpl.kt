package id.heycoding.sportstesiyen.data.source

import id.heycoding.sportstesiyen.data.source.response.NewsSportResponse
import id.heycoding.sportstesiyen.data.source.service.NewsServices
import kotlinx.coroutines.flow.flow

class NewsDataSourceImpl(
    private val newsServices: NewsServices
) : NewsDataSource {
    override suspend fun getTopHeadlineNews(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ): NewsSportResponse{
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
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): NewsSportResponse{
        return newsServices.getEverythingTeamsNewsSport(
            newsUrl = url,
            q = query,
            lang = language,
            fr = from,
            to = to,
            sortBy = sortBy,
            apiKey = apiKey
        )
    }

}