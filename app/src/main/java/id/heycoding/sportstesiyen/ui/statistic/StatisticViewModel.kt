package id.heycoding.sportstesiyen.ui.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StatisticViewModel(private val soccerUseCase: SoccerUseCase) : ViewModel() {

    private val _statisticUiState = MutableStateFlow<StatisticUiState>(StatisticUiState.Idle)
    val statisticUiState: StateFlow<StatisticUiState> = _statisticUiState

    fun getStatisticLeagueData(idLeague: String, season: String) = viewModelScope.launch {
        soccerUseCase.getStatisticUseCase(
            idLeague = idLeague,
            seasonLeague = season
        ).onStart {
            _statisticUiState.value = StatisticUiState.Loading
        }.catch { throwable ->
            _statisticUiState.value = StatisticUiState.Error(throwable.message)
        }.collectLatest { dataStatistic ->
            _statisticUiState.value = StatisticUiState.Success(dataStatistic)
        }
    }


    fun getLeagueData() = viewModelScope.launch {
        soccerUseCase.getLeagueUseCase()
            .catch { throwable ->
                _statisticUiState.value = StatisticUiState.Error(throwable.message)
            }.collectLatest { dataLeague ->
                _statisticUiState.value = StatisticUiState.Success(dataLeague)
            }
    }

    fun getSeasonData(idLeague: String) = viewModelScope.launch {
        soccerUseCase.getSeasonUseCase(
            idLeague = idLeague
        ).catch { throwable ->
            _statisticUiState.value = StatisticUiState.Error(throwable.message)
        }.collectLatest { dataSeason ->
            _statisticUiState.value = StatisticUiState.Success(dataSeason)

        }
    }
}