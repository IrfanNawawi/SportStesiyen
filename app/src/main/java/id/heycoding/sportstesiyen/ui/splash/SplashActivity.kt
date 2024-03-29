package id.heycoding.sportstesiyen.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.heycoding.sportstesiyen.databinding.ActivitySplashBinding
import id.heycoding.sportstesiyen.ui.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    private var _activitySplashBinding: ActivitySplashBinding? = null
    private val activitySplashBinding get() = _activitySplashBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding?.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        Handler().postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activitySplashBinding = null
    }
}