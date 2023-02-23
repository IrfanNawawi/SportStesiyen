package id.heycoding.sportstesiyen

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import id.heycoding.sportstesiyen.di.networkModule
import id.heycoding.sportstesiyen.di.repositoryModule
import id.heycoding.sportstesiyen.di.useCaseModule
import id.heycoding.sportstesiyen.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    private var appContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        appContext = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}