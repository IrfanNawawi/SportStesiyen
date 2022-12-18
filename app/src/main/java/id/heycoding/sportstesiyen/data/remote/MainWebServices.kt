package id.heycoding.sportstesiyen.data.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import id.heycoding.sportstesiyen.data.local.AllSportsResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface MainWebServices {

    @GET(EndPoint.Sports.GET_ALL_SPORTS)
    fun getAllSports(): Observable<AllSportsResponse>

    companion object {

        private val gson = GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        fun create(): MainWebServices {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(EndPoint.BASE_URL)
                .client(okHttpClient)
                .build()
                .create(MainWebServices::class.java)
        }
    }

    object EndPoint {
        const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/2/"

        object Sports {
            const val GET_ALL_SPORTS = "all_sports.php"
        }
    }
}