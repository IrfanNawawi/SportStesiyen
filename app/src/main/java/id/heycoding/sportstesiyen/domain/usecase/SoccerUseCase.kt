package id.heycoding.sportstesiyen.domain.usecase

import id.heycoding.sportstesiyen.domain.model.*
import id.heycoding.sportstesiyen.domain.repository.SoccerRepository
import kotlinx.coroutines.flow.Flow

class SoccerUseCase(private val soccerRepository: SoccerRepository) {
    fun getEventLeagueUseCase(
        idLeague: String,
        seasonLeague: String
    ): Flow<List<Event>> {
        return soccerRepository.getEventLeague(idLeague = idLeague, seasonLeague = seasonLeague)
    }

    fun getTeamsLeagueUseCase(
        league: String
    ): Flow<List<Teams>> {
        return soccerRepository.getTeamsLeague(
            league = league
        )

    }

    fun getJerseyTeamUseCase(
        idTeam: String
    ): Flow<List<Jersey>> {
        return soccerRepository.getJerseyTeamsDetail(
            idTeam = idTeam
        )
    }

    fun getStatisticUseCase(
        idLeague: String, seasonLeague: String
    ): Flow<List<Statistic>> {
        return soccerRepository.getStatisticTableLeague(
            idLeague = idLeague,
            seasonLeague = seasonLeague
        )
    }

    fun getLeagueUseCase(): Flow<List<Leagues>> {
        return soccerRepository.getLeague()
    }

    fun getSeasonUseCase(idLeague: String): Flow<List<Seasons>> {
        return soccerRepository.getSeasonLeague(idLeague = idLeague)
    }
}