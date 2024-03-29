package id.heycoding.sportstesiyen.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
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

        setupObserve()
        setupUI()
        setupIndicator()
        setCurrectIndicator(0)
    }

    private fun setupObserve() {
        onBoardingViewModel.apply {
            val onBoarding = getOnBoarding()
            onBoardingAdapter.setOnBoardingData(onBoarding)
        }
    }

    private fun setupUI() {
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