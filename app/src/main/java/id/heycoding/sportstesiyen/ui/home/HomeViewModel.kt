package id.heycoding.sportstesiyen.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.heycoding.sportstesiyen.domain.usecase.NewsUseCase
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import id.heycoding.sportstesiyen.ui.home.banner.BannerData
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstNews
import id.heycoding.sportstesiyen.utils.DataDummy
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val soccerUseCase: SoccerUseCase,
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private var auth: FirebaseAuth = Firebase.auth

    fun doSignOut() = viewModelScope.launch {
        auth.signOut()
        _homeUiState.value = HomeUiState.Validate
    }

    fun getBannerData(): List<BannerData> = DataDummy.generateDummyBanner()

    fun getEventLeagueData() = viewModelScope.launch {
        soccerUseCase.getEventLeagueUseCase(
            idLeague = Const.ID_LEAGUE_THESPORTDB,
            seasonLeague = Const.YEAR_SEASON_LEAGUE_THESPORTDB
        ).onStart {
            _homeUiState.value = HomeUiState.Loading
        }.catch { throwable ->
            _homeUiState.value = HomeUiState.Error(throwable.message)
        }.collectLatest { dataEvent ->
            _homeUiState.value = HomeUiState.SuccessEvents(dataEvent)
        }
    }

    fun getTeamsData() = viewModelScope.launch {
        soccerUseCase.getTeamsLeagueUseCase(
            league = Const.LEAGUE_THESPORTDB
        ).onStart {
            _homeUiState.value = HomeUiState.Loading
        }.catch { throwable ->
            _homeUiState.value = HomeUiState.Error(throwable.message)
        }.collectLatest { dataTeams ->
            _homeUiState.value = HomeUiState.SuccessTeams(dataTeams)
        }
    }


    fun getTopHeadlineNewsSportData() = viewModelScope.launch {
        newsUseCase.getTopHeadlineNewsSportDataUseCase(
            url = Const.BASE_URL_NEWSAPI + ConstNews.GET_TOP_HEADLINES_NEWS_SPORT,
            country = Const.COUNTRY_US_NEWSAPI,
            category = Const.CATEGORY_NEWSAPI,
            apiKey = Const.API_KEY_NEWSAPI
        ).onStart {
            _homeUiState.value = HomeUiState.Loading
        }.catch { throwable ->
            _homeUiState.value = HomeUiState.Error(throwable.message)
        }.collectLatest { dataTopHeadline ->
            _homeUiState.value = HomeUiState.SuccessNews(dataTopHeadline)
        }
    }
}