package id.heycoding.sportstesiyen.di

import id.heycoding.sportstesiyen.data.source.NewsDataSource
import id.heycoding.sportstesiyen.data.source.NewsDataSourceImpl
import id.heycoding.sportstesiyen.data.source.SoccerDataSource
import id.heycoding.sportstesiyen.data.source.SoccerDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<SoccerDataSource> {
        SoccerDataSourceImpl(get())
    }
    single<NewsDataSource> {
        NewsDataSourceImpl(get())
    }
}