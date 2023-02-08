package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.data.entity.Articles
import id.heycoding.sportstesiyen.data.entity.NewsSportResponse
import id.heycoding.sportstesiyen.domain.usecase.NewsUseCase
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstNews
import id.heycoding.sportstesiyen.utils.ResultState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _listEverythingNewsSportData = MutableLiveData<List<Articles>>()
    val listEverythingNewsSportData: LiveData<List<Articles>> =
        _listEverythingNewsSportData

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
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataEverything ->
                when (dataEverything) {
                    is ResultState.Success -> {
                        val response = dataEverything.data as NewsSportResponse
                        if (response.totalResults != 0) {
                            _listEverythingNewsSportData.postValue(response.articles)
                        } else {
                            _isError.postValue("No data")
                        }
                    }

                    is ResultState.Message -> {
                        _isError.postValue(dataEverything.message)
                    }
                }
            }
        }
    }
}