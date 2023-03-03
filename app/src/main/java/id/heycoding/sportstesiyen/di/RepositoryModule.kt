package id.heycoding.sportstesiyen.di

import id.heycoding.sportstesiyen.data.repository.NewsRepository
import id.heycoding.sportstesiyen.data.repository.NewsRepositoryImpl
import id.heycoding.sportstesiyen.data.repository.SoccerRepository
import id.heycoding.sportstesiyen.data.repository.SoccerRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::SoccerRepositoryImpl) bind SoccerRepository::class
    singleOf(::NewsRepositoryImpl) bind NewsRepository::class
}