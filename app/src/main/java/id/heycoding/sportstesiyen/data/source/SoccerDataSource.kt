package id.heycoding.sportstesiyen.data.source

import id.heycoding.sportstesiyen.data.source.response.*
import kotlinx.coroutines.flow.Flow

interface SoccerDataSource {
    suspend fun getEventLeague(
        idLeague: String,
        seasonLeague: String
    ): EventLeagueResponse

    suspend fun getTeamsLeague(league: String): TeamsLeagueResponse

    suspend fun getJerseyTeamsDetail(idTeam: String): JerseyTeamResponse

    suspend fun getLeague(): LeagueResponse

    suspend fun getSeasonLeague(idLeague: String): SeasonResponse

    suspend fun getStatisticTableLeague(
        idLeague: String,
        seasonLeague: String
    ): StatisticTableResponse
}