package id.heycoding.sportstesiyen.ui.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.data.response.*
import id.heycoding.sportstesiyen.data.source.response.*
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import id.heycoding.sportstesiyen.utils.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StatisticViewModel(private val soccerUseCase: SoccerUseCase) : ViewModel() {

    private val _listStatisticData = MutableLiveData<List<Table>>()
    val listStatisticData: LiveData<List<Table>> = _listStatisticData

    private val _listLeagueData = MutableLiveData<List<League>>()
    val listLeagueData: LiveData<List<League>> = _listLeagueData

    private val _listSeasonData = MutableLiveData<List<Season>>()
    val listSeasonData: LiveData<List<Season>> = _listSeasonData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getStatisticLeagueData(idLeague: String, season: String) {
        viewModelScope.launch {
            soccerUseCase.getStatisticUseCase(
                idLeague = idLeague,
                seasonLeague = season
            ).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataStatistic ->
                when (dataStatistic) {
                    is UiState.Success -> {
                        val response = dataStatistic.data as StatisticTableResponse
                        if (response.table != null) {
                            _listStatisticData.postValue(response.table)
                        } else {
                            _isEmpty.value = true
                        }
                    }

                    is UiState.Error -> {
                        _isError.postValue(dataStatistic.message)
                    }
                }
            }
        }
    }

    fun getLeagueData() {
        viewModelScope.launch {
            soccerUseCase.getLeagueUseCase()
                .onStart {
                }.onCompletion {
                }.catch { throwable ->
                    _isError.postValue(throwable.message)
                }.collectLatest { dataLeague ->
                    when (dataLeague) {
                        is UiState.Success -> {
                            val response = dataLeague.data as LeagueResponse
                            _listLeagueData.postValue(response.leagues)
                        }

                        is UiState.Error -> {
                            _isError.postValue(dataLeague.message)
                        }
                    }

                }
        }
    }

    fun getSeasonData(idLeague: String) {
        viewModelScope.launch {
            soccerUseCase.getSeasonUseCase(
                idLeague = idLeague
            ).onStart {
            }.onCompletion {
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataSeason ->
                when (dataSeason) {
                    is UiState.Success -> {
                        val response = dataSeason.data as SeasonResponse
                        _listSeasonData.postValue(response.seasons)
                    }

                    is UiState.Error -> {
                        _isError.postValue(dataSeason.message)
                    }
                }

            }
        }
    }
}