package id.heycoding.sportstesiyen.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.heycoding.sportstesiyen.data.remote.MainWebServices
import id.heycoding.sportstesiyen.data.remote.MainWebServices.EndPoint.BASE_URL_NEWSAPI
import id.heycoding.sportstesiyen.data.remote.MainWebServices.EndPoint.BASE_URL_THESPORTDB
import id.heycoding.sportstesiyen.data.remote.response.ArticlesItem
import id.heycoding.sportstesiyen.data.remote.response.SportsItem
import id.heycoding.sportstesiyen.data.remote.response.TeamsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val _listSportData = MutableLiveData<List<SportsItem>>()
    val listSportData: LiveData<List<SportsItem>> = _listSportData

    private val _listNewsSportData = MutableLiveData<List<ArticlesItem>>()
    val listNewsSportData: LiveData<List<ArticlesItem>> = _listNewsSportData

    private val _listTeamLeagueData = MutableLiveData<List<TeamsItem>>()
    val listTeamsLeagueData: LiveData<List<TeamsItem>> = _listTeamLeagueData

    private val _isLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val servicesTheSportDB = MainWebServices(BASE_URL_THESPORTDB)
    private val servicesNewsAPI = MainWebServices(BASE_URL_NEWSAPI)

    fun getAllSportsData() {
        servicesTheSportDB.getAllSports()
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

    fun getNewsSportData() {
        servicesNewsAPI.getAllNewsSport(
            MainWebServices.EndPoint.COUNTRY_NEWSAPI,
            MainWebServices.EndPoint.CATEGORY_NEWSAPI,
            MainWebServices.EndPoint.API_KEY_NEWSAPI
        )
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
                _listNewsSportData.postValue(it.articles)
            }, {
                _message.value = it.message
            })
    }

    fun getTeamLeagueData(league: String) {
        servicesTheSportDB.getAllTeamsInLeague(league)
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
                _listTeamLeagueData.postValue(it.teams)
            }, {
                _message.value = it.message
            })
    }
}