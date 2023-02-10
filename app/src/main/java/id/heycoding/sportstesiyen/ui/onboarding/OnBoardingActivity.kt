package id.heycoding.sportstesiyen.ui.onboarding

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.ActivityOnboardingBinding
import id.heycoding.sportstesiyen.ui.auth.AuthActivity

class OnBoardingActivity : AppCompatActivity() {

    private var _activityOnboardingBinding: ActivityOnboardingBinding? = null
    private val activityOnboardingBinding get() = _activityOnboardingBinding
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()
    private lateinit var onBoardingAdapter: OnBoardingAdapter
    private val listOnBoardingData = ArrayList<OnBoardingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityOnboardingBinding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(activityOnboardingBinding?.root)

        supportActionBar?.title = ""
        onBoardingAdapter = OnBoardingAdapter(listOnBoardingData)

        if (isOnline(this)) {
            initViewModel()
            initViews()
            setupIndicator()
            setCurrectIndicator(0)
        } else {
            showErrorConnection()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun showErrorConnection() {
        val view = layoutInflater.inflate(R.layout.popup_error_connection, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val tvRetryConnectionHome: TextView = view.findViewById(R.id.tv_retry_connection_home)
        tvRetryConnectionHome.setOnClickListener { dialog.cancel() }
    }
    private fun initViewModel() {
        onBoardingViewModel.apply {
            val onBoarding = getOnBoarding()
            onBoardingAdapter.setOnBoardingData(onBoarding)
        }
    }

    private fun initViews() {
        activityOnboardingBinding?.apply {
            vpOnboarding.apply {
                adapter = onBoardingAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        setCurrectIndicator(position)
                    }
                })
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }

            tvStartedOnboarding.setOnClickListener {
                if (vpOnboarding.currentItem + 1 < onBoardingAdapter.itemCount) {
                    vpOnboarding.currentItem += 1
                } else {
                    navigateToAuthActivity()
                }
            }

            tvSkipOnboarding.setOnClickListener {
                navigateToAuthActivity()
            }
        }
    }

    private fun navigateToAuthActivity() {
        startActivity(Intent(applicationContext, AuthActivity::class.java))
        finish()
    }

    private fun setupIndicator() {
        val indicators = arrayOfNulls<ImageView>(onBoardingAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                activityOnboardingBinding?.llIndicatorOnboarding?.addView(it)
            }
        }
    }

    private fun setCurrectIndicator(position: Int) {
        val childCount = activityOnboardingBinding?.llIndicatorOnboarding?.childCount
        for (i in 0 until childCount!!) {
            val imageView =
                activityOnboardingBinding?.llIndicatorOnboarding?.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityOnboardingBinding = null
    }
}