package id.heycoding.sportstesiyen.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.ActivityAuthBinding
import id.heycoding.sportstesiyen.ui.auth.login.LoginFragment

class AuthActivity : AppCompatActivity() {

    private var _activityAuthBinding: ActivityAuthBinding? = null
    private val activityAuthBinding get() = _activityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityAuthBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(activityAuthBinding?.root)

        supportActionBar?.hide()
        moveToFragment(LoginFragment())
    }

    fun moveToFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().replace(R.id.frag_container_auth, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityAuthBinding = null
    }
}