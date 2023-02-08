package id.heycoding.sportstesiyen.ui.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.data.entity.*
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.Const.ID_LEAGUE_THESPORTDB
import id.heycoding.sportstesiyen.utils.Const.YEAR_SEASON_LEAGUE_THESPORTDB
import id.heycoding.sportstesiyen.utils.ResultState
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

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getStatisticLeagueData() {
        viewModelScope.launch {
            soccerUseCase.getStatisticUseCase(
                idLeague = ID_LEAGUE_THESPORTDB,
                seasonLeague = YEAR_SEASON_LEAGUE_THESPORTDB
            ).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataStatistic ->
                when (dataStatistic) {
                    is ResultState.Success -> {
                        val response = dataStatistic.data as StatisticTableResponse
                        _listStatisticData.postValue(response.table)
                    }

                    is ResultState.Message -> {
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
                    _isLoading.value = true
                }.onCompletion {
                    _isLoading.value = false
                }.catch { throwable ->
                    _isError.postValue(throwable.message)
                }.collectLatest { dataLeague ->
                    when (dataLeague) {
                        is ResultState.Success -> {
                            val response = dataLeague.data as LeagueResponse
                            _listLeagueData.postValue(response.leagues)
                        }

                        is ResultState.Message -> {
                            _isError.postValue(dataLeague.message)
                        }
                    }

                }
        }
    }

    fun getSeasonData() {
        viewModelScope.launch {
            soccerUseCase.getSeasonUseCase(
                idLeague = Const.ID_LEAGUE_THESPORTDB
            ).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataSeason ->
                when (dataSeason) {
                    is ResultState.Success -> {
                        val response = dataSeason.data as SeasonResponse
                        _listSeasonData.postValue(response.seasons)
                    }

                    is ResultState.Message -> {
                        _isError.postValue(dataSeason.message)
                    }
                }

            }
        }
    }
}