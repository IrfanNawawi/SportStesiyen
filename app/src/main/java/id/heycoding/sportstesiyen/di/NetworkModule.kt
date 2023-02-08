package id.heycoding.sportstesiyen.di

import id.heycoding.sportstesiyen.data.service.NewsServices
import id.heycoding.sportstesiyen.data.service.SoccerServices
import id.heycoding.sportstesiyen.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        createOkHttpClient()
    }

    single {
        createRetrofit(get())
    }

    single {
        createSoccerServices(get())
    }

    single {
        createNewsServices(get())
    }
}

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addNetworkInterceptor(loggingInterceptor)
        connectTimeout(10, TimeUnit.MINUTES)
        readTimeout(10, TimeUnit.MINUTES)
        writeTimeout(10, TimeUnit.MINUTES)
    }.build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Const.BASE_URL_THESPORTDB)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createNewsServices(retrofit: Retrofit): NewsServices {
    return retrofit.create(NewsServices::class.java)
}

fun createSoccerServices(retrofit: Retrofit): SoccerServices {
    return retrofit.create(SoccerServices::class.java)
}