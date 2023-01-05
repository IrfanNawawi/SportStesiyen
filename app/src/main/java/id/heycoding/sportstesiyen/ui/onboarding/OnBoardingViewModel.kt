package id.heycoding.sportstesiyen.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.heycoding.sportstesiyen.data.remote.response.SportsItem
import id.heycoding.sportstesiyen.utils.DataDummy

class OnBoardingViewModel:ViewModel() {
    private val _listOnBoardingData = MutableLiveData<List<OnBoardingItem>>()
    val listOnBoardingData: LiveData<List<OnBoardingItem>> = _listOnBoardingData

    fun getOnBoarding(): List<OnBoardingItem> = DataDummy.generateDummyOnBoarding()
}