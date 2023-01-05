package id.heycoding.sportstesiyen.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.databinding.ActivityOnboardingBinding
import id.heycoding.sportstesiyen.ui.MainActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var activityOnboardingBinding: ActivityOnboardingBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOnboardingBinding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(activityOnboardingBinding.root)
        supportActionBar?.title = ""

        onBoardingItems()
        setupIndicator()
        setCurrectIndicator(0)
    }

    private fun onBoardingItems() {
        onBoardingAdapter = OnBoardingAdapter(
            listOf(
                OnBoardingItem(
                    imageOnBoarding = R.drawable.svg_sport_category_onboarding,
                    titleOnBoarding = resources.getString(R.string.txt_category_onboarding),
                    descOnBoarding = resources.getString(R.string.txt_ipsum)
                ),
                OnBoardingItem(
                    imageOnBoarding = R.drawable.svg_sport_news_onboarding,
                    titleOnBoarding = resources.getString(R.string.txt_news_onboarding),
                    descOnBoarding = resources.getString(R.string.txt_ipsum)
                ),
                OnBoardingItem(
                    imageOnBoarding = R.drawable.svg_sport_listdata_onboarding,
                    titleOnBoarding = resources.getString(R.string.txt_data_player_onboarding),
                    descOnBoarding = resources.getString(R.string.txt_ipsum)
                ),
            )
        )

        activityOnboardingBinding.vpOnboarding.adapter = onBoardingAdapter
        activityOnboardingBinding.vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrectIndicator(position)
            }
        })
        activityOnboardingBinding.vpOnboarding.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        activityOnboardingBinding.tvStartedOnboarding.setOnClickListener {
            if (activityOnboardingBinding.vpOnboarding.currentItem + 1 < onBoardingAdapter.itemCount) {
                activityOnboardingBinding.vpOnboarding.currentItem += 1
            } else {
                navigateToHomeActivity()
            }
        }

        activityOnboardingBinding.tvSkipOnboarding.setOnClickListener {
            navigateToHomeActivity()
        }
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun setupIndicator() {
        val indicators = arrayOfNulls<ImageView>(onBoardingAdapter.itemCount)
        val layoutParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
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
                activityOnboardingBinding.llIndicatorOnboarding.addView(it)
            }
        }
    }

    private fun setCurrectIndicator(position: Int) {
        val childCount = activityOnboardingBinding.llIndicatorOnboarding.childCount
        for (i in 0 until childCount) {
            val imageView = activityOnboardingBinding.llIndicatorOnboarding.getChildAt(i) as ImageView
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
}