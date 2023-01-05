package id.heycoding.sportstesiyen.utils

import android.content.Context
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.ui.onboarding.OnBoardingItem

object DataDummy {
    private var context: Context? = null

    fun generateDummyOnBoarding(): List<OnBoardingItem> {
        val onBoarding = ArrayList<OnBoardingItem>()
        onBoarding.add(
            OnBoardingItem(
                imageOnBoarding = R.drawable.svg_sport_category_onboarding,
                titleOnBoarding = context?.resources?.getString(R.string.txt_category_onboarding),
                descOnBoarding = context?.resources?.getString(R.string.txt_ipsum)
            )
        )
        OnBoardingItem(
            imageOnBoarding = R.drawable.svg_sport_news_onboarding,
            titleOnBoarding = context?.resources?.getString(R.string.txt_news_onboarding),
            descOnBoarding = context?.resources?.getString(R.string.txt_ipsum)
        )
        OnBoardingItem(
            imageOnBoarding = R.drawable.svg_sport_listdata_onboarding,
            titleOnBoarding = context?.resources?.getString(R.string.txt_data_player_onboarding),
            descOnBoarding = context?.resources?.getString(R.string.txt_ipsum)
        )
        return onBoarding
    }
}