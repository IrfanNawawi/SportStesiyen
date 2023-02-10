package id.heycoding.sportstesiyen.data.source

import id.heycoding.sportstesiyen.data.source.response.*
import id.heycoding.sportstesiyen.data.source.service.SoccerServices

class SoccerDataSourceImpl(
    private val soccerServices: SoccerServices
) : SoccerDataSource {
    override suspend fun getEventLeague(
        idLeague: String,
        seasonLeague: String
    ): EventLeagueResponse {
        return soccerServices.getEventLeague(idLeague = idLeague, seasonLeague = seasonLeague)
    }

    override suspend fun getTeamsLeague(league: String): TeamsLeagueResponse {
        return soccerServices.getTeamsLeague(league = league)
    }

    override suspend fun getJerseyTeamsDetail(idTeam: String): JerseyTeamResponse {
        return soccerServices.getJerseyTeamsDetail(idTeam = idTeam)
    }

    override suspend fun getLeague(): LeagueResponse {
        return soccerServices.getAllLeague()
    }

    override suspend fun getSeasonLeague(idLeague: String): SeasonResponse {
        return soccerServices.getAllSeason(idLeague = idLeague)
    }

    override suspend fun getStatisticTableLeague(
        idLeague: String,
        seasonLeague: String
    ): StatisticTableResponse {
        return soccerServices.getStatisticTableLeague(
            idLeague = idLeague,
            seasonLeague = seasonLeague
        )
    }
}