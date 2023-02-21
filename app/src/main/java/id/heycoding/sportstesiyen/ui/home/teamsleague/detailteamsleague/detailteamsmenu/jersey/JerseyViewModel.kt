package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class JerseyViewModel(private val soccerUseCase: SoccerUseCase) : ViewModel() {

    private val _jerseyUiState = MutableStateFlow<JerseyUiState>(JerseyUiState.Idle)
    val jerseyUiState: StateFlow<JerseyUiState> = _jerseyUiState

    fun getJerseyTeamsData(idTeam: String) = viewModelScope.launch {
        soccerUseCase.getJerseyTeamUseCase(
            idTeam = idTeam
        ).onStart {
            _jerseyUiState.value = JerseyUiState.Loading
        }.catch { throwable ->
            _jerseyUiState.value = JerseyUiState.Error(throwable.message)
        }.collectLatest { dataJersey ->
            _jerseyUiState.value = JerseyUiState.Success(dataJersey)
        }
    }
}