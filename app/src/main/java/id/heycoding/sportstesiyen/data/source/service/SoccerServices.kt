package id.heycoding.sportstesiyen.data.source.service

import id.heycoding.sportstesiyen.data.source.response.*
import id.heycoding.sportstesiyen.utils.ConstSports
import retrofit2.http.GET
import retrofit2.http.Query

interface SoccerServices {
    @GET(ConstSports.GET_EVENTS_SEASON)
    suspend fun getEventLeague(
        @Query("id") idLeague: String,
        @Query("s") seasonLeague: String
    ): EventLeagueResponse

    @GET(ConstSports.GET_ALL_TEAMS_IN_LEAGUE)
    suspend fun getTeamsLeague(@Query("l") league: String): TeamsLeagueResponse

    @GET(ConstSports.GET_EQUIPMENT_TEAMS)
    suspend fun getJerseyTeamsDetail(@Query("id") idTeam: String): JerseyTeamResponse

    @GET(ConstSports.GET_STATISTIC_TABLE)
    suspend fun getStatisticTableLeague(
        @Query("l") idLeague: String,
        @Query("s") seasonLeague: String
    ): StatisticTableResponse

    @GET(ConstSports.GET_ALL_LEAGUE)
    suspend fun getAllLeague(): LeagueResponse

    @GET(ConstSports.GET_ALL_SEASON)
    suspend fun getAllSeason(@Query("id") idLeague: String): SeasonResponse
}