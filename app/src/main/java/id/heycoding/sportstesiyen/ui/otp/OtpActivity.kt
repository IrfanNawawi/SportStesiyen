package id.heycoding.sportstesiyen.ui.otp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.ActivityOtpBinding
import id.heycoding.sportstesiyen.ui.otp.inputotp.InputOtpFragment

class OtpActivity : AppCompatActivity() {

    private var _activityOtpBinding: ActivityOtpBinding? = null
    private val activityOtpBinding get() = _activityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityOtpBinding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(activityOtpBinding?.root)

        supportActionBar?.hide()
        moveToFragment(InputOtpFragment())
    }

    fun moveToFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().replace(R.id.frag_container_otp, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityOtpBinding = null
    }
}