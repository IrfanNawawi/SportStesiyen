package id.heycoding.sportstesiyen.data.repository

import id.heycoding.sportstesiyen.data.entity.*
import id.heycoding.sportstesiyen.data.service.SoccerServices
import retrofit2.Response

class SoccerRepositoryImpl(
    private val soccerServices: SoccerServices
) : SoccerRepository {
    override suspend fun getEventLeague(
        idLeague: String,
        seasonLeague: String
    ): Response<EventLeagueResponse> {
        return soccerServices.getEventLeague(idLeague = idLeague, seasonLeague = seasonLeague)
    }

    override suspend fun getTeamsLeague(league: String): Response<TeamsLeagueResponse> {
        return soccerServices.getTeamsLeague(league = league)
    }

    override suspend fun getJerseyTeamsDetail(idTeam: String): Response<JerseyTeamResponse> {
        return soccerServices.getJerseyTeamsDetail(idTeam = idTeam)
    }

    override suspend fun getLeague(): Response<LeagueResponse> {
        return soccerServices.getAllLeague()
    }

    override suspend fun getSeasonLeague(idLeague: String): Response<SeasonResponse> {
        return soccerServices.getAllSeason(idLeague = idLeague)
    }

    override suspend fun getStatisticTableLeague(
        idLeague: String,
        seasonLeague: String
    ): Response<StatisticTableResponse> {
        return soccerServices.getStatisticTableLeague(idLeague = idLeague, seasonLeague = seasonLeague)
    }
}