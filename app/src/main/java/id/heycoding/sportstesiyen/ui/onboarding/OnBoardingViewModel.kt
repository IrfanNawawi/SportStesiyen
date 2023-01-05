package id.heycoding.sportstesiyen.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.heycoding.sportstesiyen.utils.DataDummy

class OnBoardingViewModel : ViewModel() {
    fun getOnBoarding(): List<OnBoardingItem> = DataDummy.generateDummyOnBoarding()
}