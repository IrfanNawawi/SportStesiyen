package id.heycoding.sportstesiyen.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    single(named(SportsDispatchers.IO)) {
        Dispatchers.IO
    }

    single(named(SportsDispatchers.Main)) {
        Dispatchers.Main
    }

    single(named(SportsDispatchers.Default)) {
        Dispatchers.Default
    }
}

object SportsDispatchers {
    const val IO = "io"
    const val Main = "main"
    const val Default = "default"
}
