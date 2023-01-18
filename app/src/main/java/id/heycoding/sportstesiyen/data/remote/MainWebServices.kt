package id.heycoding.sportstesiyen.data.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import id.heycoding.sportstesiyen.data.remote.response.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MainWebServices {

    @GET(EndPoint.Sports.GET_EVENTS_SEASON)
    fun getAllEventLeague(
        @Query("id") idLeague: String,
        @Query("s") seasonLeague: String
    ): Observable<AllEventLeagueResponse>

    @GET(EndPoint.Sports.GET_ALL_TEAMS_IN_LEAGUE)
    fun getAllTeamsLeague(@Query("l") league: String): Observable<AllTeamsLeagueResponse>

    @GET(EndPoint.NewsSports.GET_TOP_HEADLINES_NEWS_SPORT)
    fun getTopHeadlineNewsSport(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Observable<TopHeadlineNewsSportResponse>

    @GET(EndPoint.NewsSports.GET_EVERYTHING_NEWS_SPORT)
    fun getEverythingNewsSport(
        @Query("q") q: String,
        @Query("language") lang: String,
        @Query("from") fr: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Observable<EverythingNewsSportResponse>

    companion object {

        private val gson = GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        operator fun invoke(baseUrl: String): MainWebServices {
            val client = OkHttpClient.Builder().apply {
                addNetworkInterceptor(loggingInterceptor)
                connectTimeout(10, TimeUnit.MINUTES)
                readTimeout(10, TimeUnit.MINUTES)
                writeTimeout(10, TimeUnit.MINUTES)
            }.build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(MainWebServices::class.java)
        }
    }

    object EndPoint {
        const val BASE_URL_THESPORTDB = "https://www.thesportsdb.com/api/v1/json/3/"
        const val ID_LEAGUE_THESPORTDB = "4328"
        const val YEAR_SEASON_LEAGUE_THESPORTDB = "2022-2023"

        const val BASE_URL_NEWSAPI = "https://newsapi.org/v2/"
        const val API_KEY_NEWSAPI = "835d5b8b0de0440ab3eb8aaf2410d374"
        const val CATEGORY_NEWSAPI = "sport"
        const val COUNTRY_US_NEWSAPI = "us"
        const val COUNTRY_ID_NEWSAPI = "id"
        const val FROM_NEWSAPI = "2023-01-01"
        const val TO_NEWSAPI = "2023-12-31"
        const val SORT_NEWSAPI = "popularity"

        object Sports {
            const val GET_ALL_TEAMS_IN_LEAGUE = "search_all_teams.php"
            const val GET_EVENTS_SEASON = "eventsseason.php"
        }

        object NewsSports {
            const val GET_TOP_HEADLINES_NEWS_SPORT = "top-headlines"
            const val GET_EVERYTHING_NEWS_SPORT = "everything"
        }
    }
}