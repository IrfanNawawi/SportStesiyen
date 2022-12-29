package id.heycoding.sportstesiyen.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.ActivitySplashBinding
import id.heycoding.sportstesiyen.ui.MainActivity

class SplashActivity : AppCompatActivity() {
    private var activitySplashBinding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding?.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        activitySplashBinding = null
    }
}