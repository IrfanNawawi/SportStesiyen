package id.heycoding.sportstesiyen.di

import id.heycoding.sportstesiyen.domain.usecase.NewsUseCase
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        SoccerUseCase(get())
    }
    factory {
        NewsUseCase(get())
    }
}