package id.heycoding.sportstesiyen.domain.repository

import id.heycoding.sportstesiyen.domain.model.*
import kotlinx.coroutines.flow.Flow

interface SoccerRepository {
    fun getEventLeague(
        idLeague: String,
        seasonLeague: String
    ): Flow<List<Event>>

    fun getTeamsLeague(league: String): Flow<List<Teams>>

    fun getJerseyTeamsDetail(idTeam: String): Flow<List<Jersey>>

    fun getLeague(): Flow<List<Leagues>>

    fun getSeasonLeague(idLeague: String): Flow<List<Seasons>>

    fun getStatisticTableLeague(
        idLeague: String,
        seasonLeague: String
    ): Flow<List<Statistic>>
}