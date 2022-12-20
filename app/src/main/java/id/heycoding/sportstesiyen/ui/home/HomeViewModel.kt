package id.heycoding.sportstesiyen.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.heycoding.sportstesiyen.data.local.SportsItem
import id.heycoding.sportstesiyen.data.remote.MainWebServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val _listSportData = MutableLiveData<List<SportsItem>>()
    val listSportData: LiveData<List<SportsItem>> = _listSportData

    private val _isLoading = MutableLiveData<Boolean>()
    private val _message = MutableLiveData<String>()
    private val services = MainWebServices.create()

    fun getAllSportsData() {
        services.getAllSports()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoading.value = true
            }
            .doOnError {
                _message.value = it.message
            }
            .subscribe({
                _isLoading.value = false
                _listSportData.postValue(it.sports)
            }, {
                _message.value = it.message
            })
    }
}