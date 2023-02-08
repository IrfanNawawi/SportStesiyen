package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.heycoding.sportstesiyen.data.entity.Equipment
import id.heycoding.sportstesiyen.data.entity.JerseyTeamResponse
import id.heycoding.sportstesiyen.domain.usecase.SoccerUseCase
import id.heycoding.sportstesiyen.utils.ResultState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class JerseyViewModel(private val soccerUseCase: SoccerUseCase) : ViewModel() {
    private val _listJerseyData = MutableLiveData<List<Equipment>>()
    val listJerseyData: LiveData<List<Equipment>> =
        _listJerseyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getJerseyTeamsData(idTeam: String) {
        viewModelScope.launch {
            soccerUseCase.getJerseyTeamUseCase(
                idTeam = idTeam
            ).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch { throwable ->
                _isError.postValue(throwable.message)
            }.collectLatest { dataJersey ->
                when (dataJersey) {
                    is ResultState.Success -> {
                        val response = dataJersey.data as JerseyTeamResponse
                        _listJerseyData.postValue(response.equipment)
                    }

                    is ResultState.Message -> {
                        _isError.postValue(dataJersey.message)
                    }
                }
            }
        }
    }
}