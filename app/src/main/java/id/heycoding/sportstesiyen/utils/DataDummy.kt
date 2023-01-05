package id.heycoding.sportstesiyen.utils

import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.ui.onboarding.OnBoardingItem

object DataDummy {
    fun generateDummyOnBoarding(): ArrayList<OnBoardingItem> {
        val onBoarding = ArrayList<OnBoardingItem>()
        onBoarding.add(
            OnBoardingItem(
                imageOnBoarding = R.drawable.svg_sport_category_onboarding,
                titleOnBoarding = "Category Sport",
                descOnBoarding = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        onBoarding.add(
            OnBoardingItem(
                imageOnBoarding = R.drawable.svg_sport_news_onboarding,
                titleOnBoarding = "News Sport",
                descOnBoarding = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        onBoarding.add(
            OnBoardingItem(
                imageOnBoarding = R.drawable.svg_sport_listdata_onboarding,
                titleOnBoarding = "Data Player Sport",
                descOnBoarding = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
        return onBoarding
    }

}