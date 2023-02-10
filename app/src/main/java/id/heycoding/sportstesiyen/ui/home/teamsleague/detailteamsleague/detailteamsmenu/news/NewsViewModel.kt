package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.data.source.response.NewsSportResponse
import id.heycoding.sportstesiyen.domain.usecase.NewsUseCase
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstNews
import id.heycoding.sportstesiyen.utils.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _listEverythingNewsSportData = MutableLiveData<List<Articles>>()
    val listEverythingNewsSportData: LiveData<List<Articles>> =
        _listEverythingNewsSportData

    private val _uiState = MutableStateFlow<UiState<List<Articles>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Articles>>> = _uiState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getEverythingNewsSportData(query: String, date: String) {
        viewModelScope.launch {
            newsUseCase.getEverythingTeamsNewsSportDataUseCase(
                url = Const.BASE_URL_NEWSAPI + ConstNews.GET_EVERYTHING_NEWS_SPORT,
                query = query,
                language = Const.COUNTRY_ID_NEWSAPI,
                from = date,
                to = Const.TO_NEWSAPI,
                sortBy = Const.SORT_NEWSAPI,
                apiKey = Const.API_KEY_NEWSAPI
            ).onStart {
                _uiState.value = UiState.Loading
            }.onCompletion {
                _uiState.value = UiState.Loading
            }.catch { throwable ->
                _uiState.value = UiState.Error(throwable.message.toString())
            }.collectLatest {
                _uiState.value = UiState.Success(data = it)
            }
        }
    }
}