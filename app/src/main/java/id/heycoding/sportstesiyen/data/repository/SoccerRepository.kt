package id.heycoding.sportstesiyen.data.repository

import id.heycoding.sportstesiyen.data.entity.*
import retrofit2.Response

interface SoccerRepository {
    suspend fun getEventLeague(
        idLeague: String,
        seasonLeague: String
    ): Response<EventLeagueResponse>

    suspend fun getTeamsLeague(league: String): Response<TeamsLeagueResponse>

    suspend fun getJerseyTeamsDetail(idTeam: String): Response<JerseyTeamResponse>

    suspend fun getLeague(): Response<LeagueResponse>

    suspend fun getSeasonLeague(idLeague: String): Response<SeasonResponse>

    suspend fun getStatisticTableLeague(
        idLeague: String,
        seasonLeague: String
    ): Response<StatisticTableResponse>
}