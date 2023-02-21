package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.domain.usecase.NewsUseCase
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstNews
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private val _newsUiState = MutableStateFlow<NewsUiState>(NewsUiState.Idle)
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    fun getEverythingNewsSportData(query: String, date: String) = viewModelScope.launch {
        newsUseCase.getEverythingTeamsNewsSportDataUseCase(
            url = Const.BASE_URL_NEWSAPI + ConstNews.GET_EVERYTHING_NEWS_SPORT,
            query = query,
            language = Const.COUNTRY_ID_NEWSAPI,
            from = date,
            to = Const.TO_NEWSAPI,
            sortBy = Const.SORT_NEWSAPI,
            apiKey = Const.API_KEY_NEWSAPI
        ).onStart {
            _newsUiState.value = NewsUiState.Loading
        }.catch { throwable ->
            _newsUiState.value = NewsUiState.Error(throwable.message)
        }.collectLatest { dataNews ->
            _newsUiState.value = NewsUiState.Success(dataNews)
        }
    }

}