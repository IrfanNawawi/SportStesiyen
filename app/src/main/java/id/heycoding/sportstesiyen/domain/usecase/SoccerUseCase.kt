package id.heycoding.sportstesiyen.domain.usecase

import id.buaja.news.untils.fetchError
import id.heycoding.sportstesiyen.data.repository.SoccerRepository
import id.heycoding.sportstesiyen.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SoccerUseCase(private val soccerRepository: SoccerRepository) {
    suspend fun getEventLeagueUseCase(
        idLeague: String,
        seasonLeague: String
    ) = flow {
        val response =
            soccerRepository.getEventLeague(idLeague = idLeague, seasonLeague = seasonLeague)
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTeamsLeagueUseCase(
        league: String
    ) = flow {
        val response = soccerRepository.getTeamsLeague(
            league = league
        )
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getJerseyTeamUseCase(
        idTeam: String
    ) = flow {
        val response = soccerRepository.getJerseyTeamsDetail(
            idTeam = idTeam
        )
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getStatisticUseCase(
        idLeague: String, seasonLeague: String
    ) = flow {
        val response = soccerRepository.getStatisticTableLeague(
            idLeague = idLeague,
            seasonLeague = seasonLeague
        )
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getLeagueUseCase() = flow {
        val response = soccerRepository.getLeague()
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }

    suspend fun getSeasonUseCase(idLeague: String) = flow {
        val response = soccerRepository.getSeasonLeague(idLeague = idLeague)
        if (response.isSuccessful) {
            emit(ResultState.Success(response.body()))
        } else {
            emit(fetchError(response))
        }
    }
}