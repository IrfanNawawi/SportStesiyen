package id.heycoding.sportstesiyen.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.data.remote.MainWebServices
import id.heycoding.sportstesiyen.data.remote.MainWebServices.EndPoint.BASE_URL_NEWSAPI
import id.heycoding.sportstesiyen.data.remote.MainWebServices.EndPoint.BASE_URL_THESPORTDB
import id.heycoding.sportstesiyen.data.remote.response.ArticlesEverything
import id.heycoding.sportstesiyen.data.remote.response.ArticlesTopHeadline
import id.heycoding.sportstesiyen.data.remote.response.SportsItem
import id.heycoding.sportstesiyen.data.remote.response.TeamsItem
import id.heycoding.sportstesiyen.ui.home.banner.BannerData
import id.heycoding.sportstesiyen.utils.DataDummy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val _listSportData = MutableLiveData<List<SportsItem>>()
    val listSportData: LiveData<List<SportsItem>> = _listSportData

    private val _listTopHeadlineNewsSportData = MutableLiveData<List<ArticlesTopHeadline>>()
    val listTopHeadlineNewsSportData: LiveData<List<ArticlesTopHeadline>> =
        _listTopHeadlineNewsSportData

    private val _listEverythingNewsSportData = MutableLiveData<List<ArticlesEverything>>()
    val listEverythingNewsSportData: LiveData<List<ArticlesEverything>> =
        _listEverythingNewsSportData

    private val _listTeamLeagueData = MutableLiveData<List<TeamsItem>>()
    val listTeamsLeagueData: LiveData<List<TeamsItem>> = _listTeamLeagueData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isValidate = MutableLiveData<Boolean>()
    val isValidate: LiveData<Boolean> = _isValidate

    private val _isCheckingAccount = MutableLiveData<String>()
    val isCheckingAccount: LiveData<String> = _isCheckingAccount

    private val servicesTheSportDB = MainWebServices(BASE_URL_THESPORTDB)
    private val servicesNewsAPI = MainWebServices(BASE_URL_NEWSAPI)

    private var auth: FirebaseAuth = Firebase.auth

    fun doCheckingUser() {
        val user = auth.currentUser
        if (user != null) {
            _isCheckingAccount.value = user.displayName
        }
    }

    fun doSignOut() {
        auth.signOut()
        _isValidate.value = true
    }

    fun getBannerData(): List<BannerData> = DataDummy.generateDummyBanner()

    fun getAllSportsData() {
        servicesTheSportDB.getAllSports()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoading.value = true
            }
            .doOnError {
                _isLoading.value = false
                _isError.value = true
            }
            .subscribe({
                _isLoading.value = false
                _listSportData.postValue(it.sports)
            }, {
                _isLoading.value = false
                _isError.value = true
            })
    }

    fun getTopHeadlineNewsSportData() {
        servicesNewsAPI.getTopHeadlineNewsSport(
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
                _isLoading.value = false
            }
            .subscribe({
                _isLoading.value = false
                _listTopHeadlineNewsSportData.postValue(it.articles)
            }, {
                _isLoading.value = false
                _isError.value = true
            })
    }

    fun getEverythingNewsSportData() {
        servicesNewsAPI.getEverythingNewsSport(
            MainWebServices.EndPoint.CATEGORY_NEWSAPI,
            MainWebServices.EndPoint.FROM_NEWSAPI,
            MainWebServices.EndPoint.TO_NEWSAPI,
            MainWebServices.EndPoint.SORT_NEWSAPI,
            MainWebServices.EndPoint.API_KEY_NEWSAPI
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoading.value = true
            }
            .doOnError {
                _isLoading.value = false
            }
            .subscribe({
                _isLoading.value = false
                _listEverythingNewsSportData.postValue(it.articles)
            }, {
                _isLoading.value = false
                _isError.value = true
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
                _isLoading.value = false
                _isError.value = true
            }
            .subscribe({
                _isLoading.value = false
                _listTeamLeagueData.postValue(it.teams)
            }, {
                _isLoading.value = false
                _isError.value = true
            })
    }
}