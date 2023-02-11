package id.heycoding.sportstesiyen.data.repository

import id.heycoding.sportstesiyen.data.*
import id.heycoding.sportstesiyen.data.source.SoccerDataSource
import id.heycoding.sportstesiyen.domain.model.*
import id.heycoding.sportstesiyen.domain.repository.SoccerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SoccerRepositoryImpl(
    private val soccerDataSource: SoccerDataSource,
    private val dispatcher: CoroutineDispatcher,
) : SoccerRepository {
    override fun getEventLeague(idLeague: String, seasonLeague: String): Flow<List<Event>> {
        return flow {
            emit(
                soccerDataSource.getEventLeague(
                    idLeague = idLeague,
                    seasonLeague = seasonLeague
                ).eventLeagues.mappingEventLeagueToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }

    override fun getTeamsLeague(league: String): Flow<List<Teams>> {
        return flow {
            emit(
                soccerDataSource.getTeamsLeague(
                    league = league
                ).teamsLeagues.mappingTeamsToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }

    override fun getJerseyTeamsDetail(idTeam: String): Flow<List<Jersey>> {
        return flow {
            emit(
                soccerDataSource.getJerseyTeamsDetail(idTeam = idTeam).equipment.mappingJerseyToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }

    override fun getLeague(): Flow<List<Leagues>> {
        return flow {
            emit(
                soccerDataSource.getLeague().leagues.mappingLeagueToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }

    override fun getSeasonLeague(idLeague: String): Flow<List<Seasons>> {
        return flow {
            emit(
                soccerDataSource.getSeasonLeague(idLeague = idLeague).seasons.mappingSeasonToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }

    override fun getStatisticTableLeague(
        idLeague: String,
        seasonLeague: String
    ): Flow<List<Statistic>> {
        return flow {
            emit(
                soccerDataSource.getStatisticTableLeague(
                    idLeague = idLeague,
                    seasonLeague = seasonLeague
                ).table.mappingStatisticToUseCaseEntity()
            )
        }.flowOn(dispatcher)
    }

}