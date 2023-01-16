package id.heycoding.sportstesiyen.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.ActivityMainBinding
import id.heycoding.sportstesiyen.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private var _activityNewsBinding: ActivityNewsBinding? = null
    private val activityNewsBinding get() = _activityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityNewsBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(activityNewsBinding?.root)
    }
}