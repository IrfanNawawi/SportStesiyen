package id.heycoding.sportstesiyen.data.repository

import id.heycoding.sportstesiyen.data.mappingNewsToUseCaseEntity
import id.heycoding.sportstesiyen.data.source.NewsDataSource
import id.heycoding.sportstesiyen.domain.model.News
import id.heycoding.sportstesiyen.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImpl(
    private val newsDataSource: NewsDataSource,
    private val dispatcher: CoroutineDispatcher,
) : NewsRepository {
    override suspend fun getTopHeadlineNews(
        url: String,
        country: String,
        category: String,
        apiKey: String
    ): Flow<List<News>> {
        return flow {
            emit(
                newsDataSource.getTopHeadlineNews(
                    url = url,
                    country = country,
                    category = category,
                    apiKey = apiKey
                ).articles.mappingNewsToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }

    override suspend fun getEverythingTeamsNews(
        url: String,
        query: String,
        language: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): Flow<List<News>> {
        return flow {
            emit(
                newsDataSource.getEverythingTeamsNews(
                    url = url,
                    query = query,
                    language = language,
                    from = from,
                    to = to,
                    sortBy = sortBy,
                    apiKey = apiKey
                ).articles.mappingNewsToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }
}