package id.heycoding.sportstesiyen.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.data.entity.*
import id.heycoding.sportstesiyen.domain.usecase.NewsUseCase
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import id.heycoding.sportstesiyen.ui.home.banner.BannerData
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstNews
import id.heycoding.sportstesiyen.utils.DataDummy
import id.heycoding.sportstesiyen.utils.ResultState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val soccerUseCase: SoccerUseCase,
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _listTeamsLeagueDataLeague = MutableLiveData<List<TeamsLeague>>()
    val listTeamsLeagueData: LiveData<List<TeamsLeague>> = _listTeamsLeagueDataLeague

    private val _listEventLeagueDataLeague = MutableLiveData<List<EventLeague>>()
    val listEventLeagueDataLeague: LiveData<List<EventLeague>> = _listEventLeagueDataLeague

    private val _listTopHeadlineNewsSportData = MutableLiveData<List<Articles>>()
    val listTopHeadlineNewsSportData: LiveData<List<Articles>> =
        _listTopHeadlineNewsSportData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getBannerData(): List<BannerData> = DataDummy.generateDummyBanner()

    fun getEventLeagueData() {
        viewModelScope.launch {
            soccerUseCase.getEventLeagueUseCase(
                idLeague = Const.ID_LEAGUE_THESPORTDB,
                seasonLeague = Const.YEAR_SEASON_LEAGUE_THESPORTDB
            ).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataEvent ->
                when (dataEvent) {
                    is ResultState.Success -> {
                        val response = dataEvent.data as EventLeagueResponse
                        _listEventLeagueDataLeague.postValue(response.eventLeagues)
                    }

                    is ResultState.Message -> {
                        _isError.postValue(dataEvent.message)
                    }
                }
            }
        }
    }

    fun getTeamsData() {
        viewModelScope.launch {
            soccerUseCase.getTeamsLeagueUseCase(
                league = Const.LEAGUE_THESPORTDB
            ).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataTeams ->
                when (dataTeams) {
                    is ResultState.Success -> {
                        val response = dataTeams.data as TeamsLeagueResponse
                        _listTeamsLeagueDataLeague.postValue(response.teamsLeagues)
                    }

                    is ResultState.Message -> {
                        _isError.postValue(dataTeams.message)
                    }
                }
            }
        }
    }

    fun getTopHeadlineNewsSportData() {
        viewModelScope.launch {
            newsUseCase.getTopHeadlineNewsSportDataUseCase(
                url = Const.BASE_URL_NEWSAPI + ConstNews.GET_TOP_HEADLINES_NEWS_SPORT,
                country = Const.COUNTRY_US_NEWSAPI,
                category = Const.CATEGORY_NEWSAPI,
                apiKey = Const.API_KEY_NEWSAPI
            ).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataTopHeadline ->
                when (dataTopHeadline) {
                    is ResultState.Success -> {
                        val response = dataTopHeadline.data as NewsSportResponse
                        _listTopHeadlineNewsSportData.postValue(response.articles)
                    }

                    is ResultState.Message -> {
                        _isError.postValue(dataTopHeadline.message)
                    }
                }
            }
        }
    }
}