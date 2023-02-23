package id.heycoding.sportstesiyen.data.service

import id.heycoding.sportstesiyen.data.entity.NewsSportResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsServices {
    @GET
    suspend fun getTopHeadlineNewsSport(
        @Url newsUrl: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsSportResponse>

    @GET
    suspend fun getEverythingTeamsNewsSport(
        @Url newsUrl: String,
        @Query("q") q: String,
        @Query("language") lang: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsSportResponse>
}