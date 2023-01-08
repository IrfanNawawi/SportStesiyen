package id.heycoding.sportstesiyen

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: MyApp? = null
            private set
    }
}